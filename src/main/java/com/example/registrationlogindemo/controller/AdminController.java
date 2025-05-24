package com.example.registrationlogindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.*;
import com.example.registrationlogindemo.repository.*;
import com.example.registrationlogindemo.service.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class AdminController {
	
	// injection de service par constructeur
	 private UserService userService;
	 // injection des autres par field 
	 @Autowired
	 private TaskRepository taskRepository;

	 @Autowired
	 private LabelRepository labelRepository;
     
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 
	 @Autowired
	 private AssignmentRepository assignmentRepository;
	

	 @Autowired
	 private TextEntryRepository textEntryRepository;

	    public AdminController(UserService userService) {
	        this.userService = userService;
	    }
	    
    
	    @Secured("ROLE_ADMIN")
	    @GetMapping("/admin/manage-users")
	    //  Authentication est stocke dans le contexte 
	    public String manageUsers(Model model, Authentication authentication) {
	    	
	        model.addAttribute("title", "Gestion des utilisateurs");
	        // recupere le user 
	        String username = authentication.getName();
	        // Récupère tous les utilisateurs depuis le service
	        List<UserDto> allUsers = userService.findAllUsersNotDeleted();



	        // Filtre les utilisateurs avec le rôle "ROLE_USER"
	        List<UserDto> userRoleUsers = allUsers.stream()
	                .filter(userDto -> "ROLE_USER".equals(userDto.getRole()))
	                .collect(Collectors.toList());

	        //  Afficher dans la console les id et noms
	        userRoleUsers.forEach(user -> {
	            System.out.println("ID: " + user.getId() + " | Nom: " + user.getFirstName() + " " + user.getLastName());
	        });

	        // on va ajouter les users dans le model 
	        model.addAttribute("users", userRoleUsers);
	        model.addAttribute("adminName", username);

	        return "manage_users"; // La vue où tu affiches les utilisateurs
	    }

	    
	    @Secured("ROLE_ADMIN")
	    @GetMapping("/admin/add-users")
	    public String showAddUserForm(Model model) {
	        model.addAttribute("title", "Ajouter un utilisateur");
	        // // Un objet vide pour remplir le formulaire
	        model.addAttribute("user", new UserDto()); 
	        
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String username = auth.getName();

	        model.addAttribute("adminName", username);
	        return "add_user"; // Ce sera le nom du fichier HTML Thymeleaf (ex: add_user.html)
	    }

	    @Secured("ROLE_ADMIN")
	    @PostMapping("/admin/add-users")
	    // on va ajouter le user passe en parametre qui se trouve dans ModelAttribute
	    public String addUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
	    	System.out.println("dkhlna flfonction ");
	        if (result.hasErrors()) {
	        	System.out.println("erreur");
	            model.addAttribute("title", "Ajouter un utilisateur");
	            return "add_user";
	        }

	        // On met automatiquement le rôle "ROLE_USER"
	        
            System.out.println(userDto.getRole());
	        // Enregistre l'utilisateur
	        userService.saveUser(userDto);
            System.out.println("user ajouter");
	        return "redirect:/admin/manage-users"; // Redirige après ajout
	    }
	    
	    // pour modifier un user 
	    @GetMapping("/admin/edit-user/{id}")
	    // le id de user est passe en parm , qui dans un champ hidden dans la vue 
	    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
	        System.out.println(id);
	        // recupere le utilisateur 
	        User user = userService.getUserById(id); // récupérer l'utilisateur depuis la base
	        if (user == null) {
	            // Gérer le cas où l'utilisateur n'existe pas
	            return "redirect:/admin/manage-users";
	        }
	        // passer les attribute de objet User dans UserDto
	        UserDto userDto = new UserDto();
	        userDto.setId(user.getId()); 
	        userDto.setFirstName(user.getName().split(" ")[0]);
	        userDto.setLastName(user.getName().split(" ")[1]);
	        userDto.setEmail(user.getEmail());
	        userDto.setRole(user.getRole());
	        // pour que on puisse remplir les champs avec les donnes de user 
	        model.addAttribute("user", userDto);
	        
	        // Récupération de l'utilisateur connecté
	        // recuperation de l'objet de authentification depuis context
		     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		     String username = auth.getName();
		     model.addAttribute("adminName", username);
	        model.addAttribute("title", "Modifier l'utilisateur");
	        return "edit_user"; 
	    }


	    
	    // le action post pour modifier le user 
	    @PostMapping("/admin/edit-user/{id}")
	    //  le id est hiden , et le objet user  se trouve dans le model 
	    public String updateUser(@PathVariable("id") Long id,
	                             @Valid @ModelAttribute("user") UserDto userDto,
	                             BindingResult result,
	                             Model model) {
	        if (result.hasErrors()) {
	            model.addAttribute("title", "Modifier l'utilisateur");
	            return "edit_user"; 
	        }
	        userService.updateUser(id, userDto);
	        return "redirect:/admin/manage-users";
	    }

	   
	    @GetMapping("/admin/delete-user/{id}")
	    public String deleteUser(@PathVariable("id") Long id) {
	        userService.deleteUserById(id);
	        // une redirection  cote client 
	        return "redirect:/admin/manage-users";
	    }
	    
	    
	    
	 
// pour creer la tache 
	    @PostMapping("/admin/tasks/create")
	    // les donnes pour la creation de tache sont envoye dans la requte de form
	    public String createTask(@RequestParam("title") String title,
	                             @RequestParam("description") String description,
	                             @RequestParam("csvFile") MultipartFile csvFile,
	                             @RequestParam("labels") String labels,
	                             @RequestParam("dateLimite") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateLimite,
	                             @RequestParam(value = "selectedUsers", required = false) List<Long> selectedUserIds) {
       try {
	            // Vérifier si un fichier a été fourni
	            if (csvFile.isEmpty()) {
	                return "redirect:/admin/tasks/create?error=file";
	            }

	            // Vérifier si des utilisateurs ont été sélectionnés
	            if (selectedUserIds == null || selectedUserIds.isEmpty()) {
	                return "redirect:/admin/tasks/create?error=nouser";
	            }

	            // Créer le dossier d'upload si non existant
	            // on va crre un dossier pour le projet pour stocker les fichiers csv 
	            String uploadDirPath = "C:/Users/INFO/Documents/workspace-spring-tool-suite-4-4.28.0.RELEASE/registration-login-springboot-security-thymeleaf/uploads";
	            File uploadDir = new File(uploadDirPath);
	            if (!uploadDir.exists()) uploadDir.mkdirs();

	            // Enregistrer le fichier CSV
	         
	            String fileName = csvFile.getOriginalFilename();
	            String filePath = uploadDirPath + "/" + fileName;
	            File file = new File(filePath);
	            csvFile.transferTo(file);

	            // Créer et sauvegarder la tâche
	            // on va setter les donnes de tache 
	            Task task = new Task();
	            task.setTitle(title);
	            task.setDescription(description);
	            task.setCsvFilePath(filePath);
	            task.setCreatedAt(LocalDateTime.now());
	            task.setDateLimite(dateLimite);


	            // Créer les labels
	            // on crree tous d abord une liste de labels , on les separe avec , 
	            List<Label> taskLabels = Arrays.stream(labels.split(","))
	                    .map(String::trim)
	                    .filter(name -> !name.isEmpty())
	                    .map(labelName -> {
	                    	// pour chaque label , on crree un objet label 
	                        Label label = new Label();
	                        // on sette son nom 
	                        label.setLabelName(labelName);
	                        // et on le enreigstre dans la table labels 
	                        return labelRepository.save(label);
	                    })
	                    .toList();
	            // ici on va stter la liste de labels d'une tache par la liste des labels que on crre 
	            // par defaut il va remolir aussi la table task_label avec le id de tache et de label
	            task.setLabels(new HashSet<>(taskLabels));


	            // on va saver le task 
	            taskRepository.save(task);

	            // Lire le contenu du fichier CSV
	            // liste des line 
	            List<String> lines = Files.readAllLines(file.toPath());
	            if (lines.size() <= 1) {
	                return "redirect:/admin/tasks/create?error=csvempty";
	            }
// datalines qui est une liste pour stocke le contenu 
	            List<String> dataLines = new ArrayList<>(lines); // prend tout le contenu

	            // on va shuffler les lignes 
	            Collections.shuffle(dataLines);

	            // Récupérer les utilisateurs sélectionnés
	            List<User> selectedUsers = userRepository.findAllById(selectedUserIds);
	            int userCount = selectedUsers.size();

	            // Assigner chaque ligne à un utilisateur
	            for (int i = 0; i < dataLines.size()-1; i++) {
	            	// creation d un nouveau instance de text_entry 
	            	// parce que text_entry represente un lige dans le fichier csv 
	                TextEntry textEntry = new TextEntry();
	                // le num de ligne 
	                textEntry.setNumLigne(i + 1);
	                textEntry.setAssigned(false);
	                textEntry.setCreatedAt(LocalDateTime.now());
	                // le task que on a crre 
	                textEntry.setTask(task);

	                // on va parcourire les users 
	                // pour choisir le user id pour le text_entry
	                User assignedUser = selectedUsers.get(i % userCount);
	                // et on va setter le user par user 
	                textEntry.setUser(assignedUser);

	                // e on va stocke la ligne dans BD 
	                textEntryRepository.save(textEntry);
	            }

	            return "redirect:/admin/tasks";

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "redirect:/admin/tasks/create?error=exception";
	        }
	    }

	 // la page ou le admin sera envoye apres login	    
		 @GetMapping("/admin/tasks")
		 public String showTaskStatistics(Model model) {
			 // recuperation de tous les taches 
		     List<Task> tasks = taskRepository.findAll();

		     // Récupération de l'utilisateur connecté
		     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		     String username = auth.getName();
		     System.out.println("Nombre de tâches récupérées : " + tasks.size());

		     
		     //list qui contient tous kes map de stat 
		     // chaque stat contient les donnes d une task 
		     List<Map<String, Object>> taskStats = new ArrayList<>();

		     // pour chaque tache 
		     for (Task task : tasks) {
		         Long taskId = task.getId();
	// le nombre de text_entry pour une tache 
		         long total = textEntryRepository.countByTaskId(taskId);
		         // les nombres de text_entry qui sont assignes 
		         long validated = assignmentRepository.countByTextEntry_TaskId(taskId);
		         double pourcentage = (total > 0) ? (validated * 100.0 / total) : 0;

		         // Ajout de la logique canDownload
		         // si tous les text_entry sont assigne 
		         // alors download est true
		         boolean canDownload = (total > 0) && (validated == total);

		         System.out.println("---- Tâche ----");
		         System.out.println("ID: " + taskId);
		         System.out.println("Titre: " + task.getTitle());
		         System.out.println("Total: " + total);
		         System.out.println("Validés: " + validated);
		         System.out.println("Pourcentage: " + pourcentage + "%");
		         System.out.println("Can Download: " + canDownload);

		         // on va stocke les donnes dans map stat 
		         Map<String, Object> stat = new HashMap<>();
		         stat.put("id", taskId);
		         stat.put("title", task.getTitle());
		         stat.put("validated", validated);
		         stat.put("total", total);
		         stat.put("pourcentage", pourcentage);
		         stat.put("canDownload", canDownload); 
		         // on va ajouter le stat dans le map de taskstat
		         taskStats.add(stat);
		     }
	// on va ajouter la liste stat 
		     model.addAttribute("adminName", username);
		     model.addAttribute("taskStats", taskStats);
		     return "tasks-list";
		 }


	 
// lorsque on clique sur le button detals pour une task 
	    @GetMapping("/admin/tasks/details/{id}")
	    public String taskDetails(@PathVariable Long id, Model model, Authentication authentication) {
	    	// on va recupere le task par id 
	        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Tâche non trouvée"));
	        // on va recupere la liste des line des cette task (text_entry)
	        List<TextEntry> textEntries = textEntryRepository.findByTaskId(id);
	        // on va recupere les les labels de task 
	        Set<Label> labels = task.getLabels();
	        // ajouter ces donnes au model 
	        model.addAttribute("task", task);
	        model.addAttribute("textEntries", textEntries);

	        // Utiliser des Maps pour stocker les contenus dynamiques
	        Map<Long, String> lineContents = new HashMap<>();
	        Map<Long, String> assignedUsers = new HashMap<>();

	        for (TextEntry entry : textEntries) {
	        	// pour chaque entry , on va recuper le path de fichier 
	            String csvFilePath = task.getCsvFilePath();
	            // le index de line dans le fichier 
	            int lineIndex = entry.getNumLigne();
	            // extraire le contenu de ligne d'apres le path de fichier et le index 
	            String lineContent = extractCsvLine(csvFilePath, lineIndex);
	            
	            // on va putter le contenu dans le map avec le id de entry 
	            lineContents.put(entry.getId(), lineContent);
	            // on va putter dans cette maps le id de entry et la valeur le user assigne a ce entry 
	            assignedUsers.put(entry.getId(), entry.getUser() != null ? entry.getUser().getName() : "Non assigné");
	        }

	        // on va recupere le nombre de text_entry par le id de task , combien de text_entry pour une tache 
	        long total = textEntryRepository.countByTaskId(id);
	        // on va recupere le nombre de entry qui sont valide ca veut dire dans la table assignement 
	        // recupere par task_id 
	        long validated = assignmentRepository.countByTextEntry_TaskId(id);
	        // pour calculer le pourcentage qui valide 
	        double pourcentage = (total > 0) ? (validated * 100.0 / total) : 0;

	        // Compter le nombre de lignes dans le fichier CSV
	        long lineCount = countCsvLines(task.getCsvFilePath())-1;
	        
	     // Extraire les utilisateurs uniques affectés à cette tâche
	        Set<User> usersAffectes = textEntries.stream()
	                .map(TextEntry::getUser)
	                .filter(Objects::nonNull)
	                .collect(Collectors.toSet());
	        // Récupération de l'utilisateur connecté
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String username = auth.getName();

	        model.addAttribute("usersAffectes", usersAffectes);

	        // Ajouter les données au modèle pour la vue
	        model.addAttribute("adminName", username);
	        model.addAttribute("task", task);
	        model.addAttribute("textEntries", textEntries);
	        model.addAttribute("lineContents", lineContents);
	        model.addAttribute("assignedUsers", assignedUsers);
	        model.addAttribute("labels", labels); // Ajout des labels
	        model.addAttribute("lineCount", lineCount); // Nombre de lignes du fichier CSV
	        model.addAttribute("pourcentage", pourcentage); // Avancement

	        return "task-details-admin";
	    }
 
	    // Méthode pour compter le nombre de lignes dans le fichier CSV
	    private long countCsvLines(String filePath) {
	        long lineCount = 0;
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            while (br.readLine() != null) {
	                lineCount++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return lineCount;
	    }


	 private String extractCsvLine(String csvFilePath, int lineIndex) {
		    try {
		        List<String> lines = Files.readAllLines(Paths.get(csvFilePath));  // Lire le fichier CSV
		        if (lines.size() > 1) {
		            List<String> dataLines = lines.subList(1, lines.size());  // Ignorer la 1ère ligne (headers)
		            if (lineIndex >= 1 && (lineIndex - 1) < dataLines.size()) {
		                String line = dataLines.get(lineIndex - 1);
		                String[] columns = line.split(";", 3); // On divise en 3 parties max
		                if (columns.length >= 3) {
		                    return columns[1].trim() + " | " + columns[2].trim();  // Retourner uniquement t1 et t2
		                }
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return "Ligne non trouvée";
		}

	   


	    // lorsque on clique sur le button supprimer (desafecter le user d 'une tache )
	    @GetMapping("/admin/tasks/unassign/confirmation/{taskId}/{userId}")
	    public String confirmUnassign(
	            @PathVariable Long taskId,
	            @PathVariable Long userId,
	            Model model) {

	    	  // Récupération de l'utilisateur connecté
		     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		     String username = auth.getName();
	    	
	    	
	        // Étape 1: Récupérer tous les TextEntry de ce user pour cette tâche
	    	// ca veut dire les entries d un user pour une tache 
	        List<TextEntry> allTextEntries = textEntryRepository.findByUserIdAndTaskId(userId, taskId);

	        // Récupérer l'utilisateur
	        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

	        // la liste des entry qui ne sont pas annotes 
	        List<TextEntry> nonAnnotatedTexts = new ArrayList<>();

	        // Étape 2: Filtrer ceux qui ne sont pas encore annotés
	        for (TextEntry te : allTextEntries) {
	            Optional<Assignment> assignmentOpt = assignmentRepository.findByUserAndTextEntry(user, te);
	            // si le assignmentOpt.isEmpty() car optional peut retourner vide 
	            if (assignmentOpt.isEmpty()) {
	            	// on va le ajouter dans cette liste 
	                nonAnnotatedTexts.add(te);
	            }
	        }

	        System.out.println("==== Étape 1 : Tous les TextEntry de l'utilisateur pour la tâche ====");
	        for (TextEntry te : allTextEntries) {
	            System.out.println("TextEntry ID: " + te.getId() + ", NumLigne: " + te.getNumLigne() + ", createdAt: " + te.getCreatedAt());
	        }

	        System.out.println("==== Étape 2 : Textes non encore annotés ====");
	        for (TextEntry te : nonAnnotatedTexts) {
	            System.out.println("TextEntry NON annoté ID: " + te.getId());
	        }

	        // Étape 3: Lire le contenu des lignes CSV correspondantes
	        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Tâche non trouvée"));
	        // recupere le path de fichier de task 
	        String csvFilePath = task.getCsvFilePath();

	        
	        // on va recuperer le contenu et le faire dans une map
	        Map<Long, String> lineContents = new HashMap<>();
	        for (TextEntry entry : nonAnnotatedTexts) {
	        	// on va recupere le content de tous les entry non assignes 
	            String content = extractCsvLine(csvFilePath, entry.getNumLigne());
	            // on va putter dans le map le id de entry et le content 
	            lineContents.put(entry.getId(), content);
	        }

	        // Étape 4: Récupérer les autres utilisateurs que le user que on veut supprimer 
	        List<User> otherUsers = userRepository.findAllExcept(userId);

	        // on va ajouter les text_entry qui sont pas annotes 
	        model.addAttribute("textEntries", nonAnnotatedTexts);
	        model.addAttribute("lineContents", lineContents);
	        model.addAttribute("otherUsers", otherUsers);
	        model.addAttribute("taskId", taskId);
	        model.addAttribute("oldUserId", userId);
	        model.addAttribute("adminName", username);
	        return "confirm_unassign";
	    }

	    
	   // lorsque on clique sur le button reaffecter  
	    @PostMapping("/admin/tasks/unassign/redistribute")
	    public String redistributeTexts(
	    		// le id de task 
	    		// le user a supprimer 
	            @RequestParam Long taskId,
	            @RequestParam Long oldUserId,
	            // les nouvelles users selectionnes 
	            @RequestParam List<Long> newUserIds,
	            Model model) {

	        // Récupérer les text_entry non encore annotés
	        List<TextEntry> entriesToRedistribute = textEntryRepository
	            .findByUserIdAndTaskIdAndNotAnnotated(oldUserId, taskId);

	        // Shuffle les textes
	        Collections.shuffle(entriesToRedistribute);

	        // Répartir équitablement entre les nouveaux utilisateurs
	        int userCount = newUserIds.size();
	        for (int i = 0; i < entriesToRedistribute.size(); i++) {
	        	// on va choisir un user 
	            Long selectedUserId = newUserIds.get(i % userCount);
	            TextEntry entry = entriesToRedistribute.get(i);
	         // Récupérer l'utilisateur à partir de son ID
	            User selectedUser = userRepository.findById(selectedUserId)
	                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

	            // Affecter l'utilisateur à l'entrée de texte
	            // on va setter le entry qui reste avec le id de user selectionnes 
	            entry.setUser(selectedUser);

	            textEntryRepository.save(entry);
	        }

	        // Optionnel : Redirection
	        return "redirect:/admin/tasks/details/" + taskId + "?success";
	    }

	    // button pour telecharger le fichier pret
	    @GetMapping("/admin/tasks/download/{id}")
	    public String prepareDownload(@PathVariable Long id, Model model) {
	        System.out.println("Préparation du téléchargement pour la tâche ID : " + id);

	        // recupere le task d;apres le id 
	        Task task = taskRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));
	        System.out.println("Tâche trouvée : " + task.getTitle());

	        // on va construire le nom de fichier 
	        String filename = "annotated-task-" + task.getTitle() + ".csv";
	        // le path de fichier 
	        Path filePath = Paths.get("downloads", filename);

	        try {
	        	// recupere la liste des entry pour la tache 
	            List<TextEntry> entries = textEntryRepository.findByTaskId(id);
	            System.out.println("Nombre de TextEntries : " + entries.size());

	            // recupere les assignemt pour les entries 
	            List<Assignment> assignments = entries.stream()
	                    .map(e -> assignmentRepository.findByTextEntry(e).orElse(null))
	                    .filter(Objects::nonNull)
	                    .toList();
	            System.out.println("Nombre d'assignments : " + assignments.size());

	            // Changer l'en-tête de fichier 
	            StringBuilder csvBuilder = new StringBuilder("Ligne,\"Texte1\",\"Texte2\",Annotation,Annotateur\n");

	            //(a) est le assignement
	            for (Assignment a : assignments) {
	            	// pour chaque assignemtn , on recupere le entry , apres le num de ligne de ce entry 
	                int lineNumber = a.getTextEntry().getNumLigne();
	                // on va recupere le le content par num de ligne et le path de fichier csv de tache 
	                String originalText = extractCsvLine(task.getCsvFilePath(), lineNumber);
	                // on va recupere le label (id) apre le labelname 
	                String label = a.getLabel().getLabelName();
	                // ici , on va recuperer le user qui a annote ca 
	                String annotator = a.getUser().getName();

	                // Séparation des deux textes par '|'
	                // le content recuepere et separe par | 
	                String[] parts = originalText.split("\\|");
	                String t1 = parts.length > 0 ? parts[0].trim().replace("\"", "\"\"") : "";
	                String t2 = parts.length > 1 ? parts[1].trim().replace("\"", "\"\"") : "";

	                System.out.println("Ligne " + lineNumber + ": " + t1 + " | " + t2 + " | " + label + " | " + annotator);

	                // on va constrcuire le ligne 
	                csvBuilder.append(lineNumber).append(",")
	                          .append("\"").append(t1).append("\",")
	                          .append("\"").append(t2).append("\",")
	                          .append(label).append(",")
	                          .append(annotator).append("\n");
	            }

	            // creation de file dans directory 
	            Files.createDirectories(filePath.getParent());
	            Files.write(filePath, csvBuilder.toString().getBytes());

	            System.out.println("Fichier CSV généré : " + filePath.toAbsolutePath());

	            model.addAttribute("downloadLink", "/admin/tasks/file/" + filename);
	            model.addAttribute("message", "Fichier prêt au téléchargement");

	            return "redirect:/admin/tasks";

	        } catch (IOException e) {
	            System.out.println("Erreur lors de la génération du fichier CSV : " + e.getMessage());
	            e.printStackTrace();

	            model.addAttribute("error", "Erreur lors de la génération du fichier");
	            return "redirect:/admin/tasks";
	        }
	    }

}