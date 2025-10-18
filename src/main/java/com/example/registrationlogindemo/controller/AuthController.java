package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.*;
import com.example.registrationlogindemo.entity.Task;
import com.example.registrationlogindemo.entity.TextEntry;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.*;
import com.example.registrationlogindemo.repository.TaskRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication; // Importation du paquet nécessaire
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AuthController {
// injection de userservice par constructeur
	 private UserService userService;
	 
	 // les autres par field 
	 @Autowired
	 private TaskRepository taskRepository;

	 @Autowired
	 private LabelRepository labelRepository;
	 
	 @Autowired
	 private AssignmentRepository assignmentRepository;
	 
	 @Autowired
	 private TextEntryRepository textEntryRepository;
	 
	 @Autowired
	 private UserRepository userRepository;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

   
    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    // enregistrer le user avec les donens en param : @ModelAttribute("user")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


    // redirection vers le home par le user stocke dans Authentification
    @GetMapping("/home")
    public String home(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login"; // 🔒 redirige vers login si non authentifié
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/user/home";
        }
    }
    
 
      // Cette annotation garantit que seul un administrateur peut accéder à cette page
    @GetMapping("/admin/home")
    @Secured("ROLE_ADMIN")
    public String adminHome(Model model, Authentication authentication) {
    	// Authentication est un objet de l'interface Authentication de Spring Security
        // Nom de l'utilisateur connecté
        String username = authentication.getName();

     // Récupère tous les utilisateurs depuis le service , pour choisir les annotateurs
        List<UserDto> allUsers = userService.findAllUsersNotDeleted();

        // Filtre les utilisateurs avec le rôle "ROLE_USER"
        List<UserDto> userRoleUsers = allUsers.stream()
                .filter(userDto -> "ROLE_USER".equals(userDto.getRole()))
                .collect(Collectors.toList());

        // ➡️ Afficher dans la console les id et noms
        userRoleUsers.forEach(user -> {
            System.out.println("ID: " + user.getId() + " | Nom: " + user.getFirstName() + " " + user.getLastName());
        });
// on a ajouter les users dans le model 
        model.addAttribute("users", userRoleUsers);
        // Ajoute les données au modèle
        model.addAttribute("message", "Bienvenue sur la page d'accueil de l'Admin!");
        model.addAttribute("adminName", username);

        return "admin_home";
    }

    
    @GetMapping("/user/home")
    @Secured("ROLE_USER")
    public String USERHome(Model model) {
        // Récupération de l'utilisateur connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByEmail(username);

        if (user == null) {
            model.addAttribute("error", "Utilisateur non trouvé !");
            return "error";
        }

        // Récupérer toutes les tâches assignées à cet utilisateur
        // par id de user (distinct)
        List<Task> tasks = textEntryRepository.findTasksByUserId(user.getId());

        // pour stocker les infos de taches 
        List<Map<String, Object>> taskInfos = new ArrayList<>();
// pour chaque tache , on crre les infos 
        for (Task task : tasks) {
            Map<String, Object> taskData = new HashMap<>();

            taskData.put("id", task.getId());
            // Récupérer les informations de base
            taskData.put("title", task.getTitle());
            taskData.put("deadline", task.getDateLimite());

            // Nombre total de lignes assignées à ce user pour cette tâche
            long totalEntries = textEntryRepository.countByTaskIdAndUserId(task.getId(), user.getId());

            // Nombre de lignes annotées (validées)
            long validatedEntries = assignmentRepository.countByTaskIdAndUserId(task.getId(), user.getId());

            // Calcul de l'avancement en pourcentage
            double progress = (totalEntries > 0) ? ((double) validatedEntries / totalEntries) * 100 : 0;

            taskData.put("taille", totalEntries);              // nombre total de TextEntry
            taskData.put("avancement", Math.round(progress));  // pourcentage d'avancement arrondi

            taskInfos.add(taskData);
        }

        // Ajouter les données au modèle pour la vue
        model.addAttribute("username", username);
        model.addAttribute("taskInfos", taskInfos);
        model.addAttribute("message", "Bienvenue sur la page d'accueil de l'utilisateur!");

        return "user_home";
    }


    
   // pour le button travailler  
   
    @GetMapping("/user/annotate/start")
    @Secured("ROLE_USER")
    // annoter un task 
    public String startAnnotation(@RequestParam("taskId") Long taskId, Principal principal, Model model) {
    	
    	// trouver le user par principal.getName()
        User user = userRepository.findByEmail(principal.getName());
        // trouver le task par le id de task dans param 
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) return "redirect:/error";

        // recuperer la liste des text_entry par user id et task id parce que ces deux ces deux sont dans la table text_entry
        List<TextEntry> entries = textEntryRepository.findByTaskIdAndUserId(taskId, user.getId());

        if (entries.isEmpty()) return "redirect:/user/task/" + taskId;
// pour la premiere fois on va le redirige avec index 0  ca veut dire premiere entry pour ce user et cette task
        // exemple : http://localhost:8080/user/annotate?taskId=7&index=0
        return "redirect:/user/annotate?taskId=" + taskId + "&index=0";
    }
    
    
    
    
    
    
    
    
    // les pages de annotation
    @GetMapping("/user/annotate")
    @Secured("ROLE_USER")
    public String annotateLine(@RequestParam Long taskId,
                               @RequestParam int index,
                               Principal principal,
                               Model model) {
// recupere le user et le task 
        User user = userRepository.findByEmail(principal.getName());
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null || user == null) {
            model.addAttribute("error", "Tâche ou utilisateur introuvable.");
            return "error";
        }
// recupere la liste des text_entry par le task_id et user_id 
        List<TextEntry> entries = textEntryRepository.findByTaskIdAndUserId(taskId, user.getId());

        if (index < 0 || index >= entries.size()) {
            model.addAttribute("error", "Index de ligne invalide.");
            return "redirect:/user/task/" + taskId;
        }
// on va recupere le index de entry 
        TextEntry entry = entries.get(index);

        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<String> allLines;
        try {
        	// recupere les lignes de cette task ca veut dire le path de fichier 
            allLines = Files.readAllLines(Paths.get(task.getCsvFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur de lecture du fichier CSV.");
            return "error";
        }
// recupere le numero de line de cette entry 
        
        int lineIndex = entry.getLineIndex();

        boolean hasHeader = !allLines.isEmpty() && allLines.get(0).toLowerCase().contains("id");
        int totalLines = hasHeader ? allLines.size() - 1 : allLines.size();

        if (lineIndex < 0 || lineIndex >= allLines.size()) {
            model.addAttribute("error", "Index hors limites.");
            return "redirect:/user/task/" + taskId;
        }
// getter le ligne avec lineIndex qui le numero de ligne dans la base de donnes et fichier csv 
        String rawLine = allLines.get(lineIndex);
        String[] parts = rawLine.split(";");

        if (parts.length < 3) {
            model.addAttribute("error", "Ligne CSV mal formée.");
            return "error";
        }

        CsvLine line = new CsvLine(Long.parseLong(parts[0].trim()), parts[1].trim(), parts[2].trim());
        System.out.println("CSV Line chargée : " + line);

        model.addAttribute("task", task);
        model.addAttribute("line", line);
        model.addAttribute("labels", task.getLabels());
        model.addAttribute("taskId", taskId);
        // current index c est le index actuel, on ajoute 1 pour le suivant 
        model.addAttribute("currentIndex", index);
        model.addAttribute("total", entries.size());
        model.addAttribute("username", username);

        return "annotate";
    }

// c est pour valide button 

    @PostMapping("/user/save-and-next")
    @Secured("ROLE_USER")
    public String saveAndNext(@RequestParam int numLigne,
                              @RequestParam Long taskId,
                              @RequestParam(required = false) Long selectedLabel,  // Peut être null si non validé
                              @RequestParam int currentIndex,
                              @RequestParam String actionType,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
    	
    	// chercher le user 
        User user = userRepository.findByEmail(principal.getName());
        // recupere le texte_entry par num de ligne te taskid et userid
        TextEntry textEntry = textEntryRepository
            .findByNumLigneAndUserIdAndTaskId(numLigne, user.getId(), taskId)
            .orElse(null);

        if (textEntry == null) {
            redirectAttributes.addFlashAttribute("error", "Données invalides.");
            return "redirect:/user/annotate?taskId=" + taskId + "&index=" + currentIndex;
        }

        // Si l'action est "validate" et que aucun label n'est sélectionné
        if ("validate".equals(actionType) && selectedLabel == null) {
            redirectAttributes.addFlashAttribute("error", "Veuillez sélectionner un label avant de valider.");
            return "redirect:/user/annotate?taskId=" + taskId + "&index=" + currentIndex;
        }

        if (selectedLabel != null) {
        	// recupere le label selectionne
            Label label = labelRepository.findById(selectedLabel).orElse(null);
            if (label != null) {
                // Enregistrer l'annotation
            	// recupere le assignement par user id et text_entry
            	// si ne existe pas on la crre 
                Assignment assignment = assignmentRepository.findByUserAndTextEntry(user, textEntry)
                    .orElseGet(() -> {
                        Assignment a = new Assignment();
                        a.setUser(user);
                        a.setTextEntry(textEntry);
                        return a;
                    });

                // et on la enregistre dans la table assigenement
                assignment.setLabel(label);
                assignment.setAnnotatedAt(LocalDateTime.now());
                assignmentRepository.save(assignment);
                redirectAttributes.addFlashAttribute("success", "Annotation enregistrée !");
            }
        }

        // Gérer les actions : previous, next, validate ou finish
        // initialiser tout d abord le index par le current index 
        int newIndex = currentIndex;
        if ("previous".equals(actionType)) {
            newIndex = Math.max(0, currentIndex - 1); // Empêche d’aller au-dessous de 0
        } else if ("next".equals(actionType)) {
            newIndex = currentIndex + 1; // Passe à l'annotation suivante
        } else if ("finish".equals(actionType)) {
            redirectAttributes.addFlashAttribute("success", "Annotation terminée !");
            return "redirect:/user/task/" + taskId; // Redirige vers la page de la tâche
        }

        // Redirection vers l'annotation suivante ou précédente
        return "redirect:/user/annotate?taskId=" + taskId + "&index=" + newIndex;
    }

    


}