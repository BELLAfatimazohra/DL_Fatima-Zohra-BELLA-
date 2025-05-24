package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	//  injection des  dependances 
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    
    // constructeur 
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // methode pour saver le user (UserDto)
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        // setter les attributs de user 
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encoder le password 
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // si par exemple le admin entre un autre role 
        // Forcer le rôle à "ROLE_ADMIN"
        user.setRole(userDto.getRole());

        System.out.println("Rôle assigné : ROLE_ADMIN");
        userRepository.save(user);
        System.out.println("Utilisateur ajouté");
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        // convertion des user en userDto
        // Crée un flux (Stream<User>) à partir de la liste des utilisateurs.
        return users.stream()
        		// this::convertEntityToDto est une référence de méthode 
        		// qui appelle la méthode convertEntityToDto() pour chaque User.
                .map(this::convertEntityToDto)
                //collecte tous les éléments du flux transformé dans une nouvelle List<UserDto>.
                .collect(Collectors.toList());
    }
// transforme un user en userDto
    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        
        
        userDto.setId(user.getId());
        
        // Gérer le nom et prénom
        String[] nameParts = user.getName().split(" ", 2); // "2" pour éviter erreur si pas d'espace
        if (nameParts.length >= 2) {
            userDto.setFirstName(nameParts[0]);
            userDto.setLastName(nameParts[1]);
        } else {
            userDto.setFirstName(user.getName());
            userDto.setLastName(""); 
        }
        
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        
        return userDto;
    }

    
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            user.setEmail(userDto.getEmail());
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            user.setRole(userDto.getRole());
            userRepository.save(user);
        }
    }

 // Suppression logique d'un utilisateur
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setDeleted(true); // Marquer comme supprimé
        userRepository.save(user); // Sauvegarder l'utilisateur avec la mise à jour
    }
    

    @Override
    public List<UserDto> findAllUsersNotDeleted() {
        List<User> users = userRepository.findByIsDeletedFalse();
        return users.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
    }

}
