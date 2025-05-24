package com.example.registrationlogindemo.security;

import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.UserRepository;
// Représente une autorité de sécurité (rôle ou autorisation).
import org.springframework.security.core.GrantedAuthority;
// Implémentation simple de GrantedAuthority. 
import org.springframework.security.core.authority.SimpleGrantedAuthority;
// Interface représentant les informations d'un utilisateur authentifié.
import org.springframework.security.core.userdetails.UserDetails;
//  Interface de Spring Security pour la récupération des informations de l'utilisateur.
import org.springframework.security.core.userdetails.UserDetailsService;
// Exception levée si l'utilisateur n'est pas trouvé. 
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// CustomUserDetailsService qui implémente l'interface UserDetailsService de Spring Security.
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	// Cette classe doit implémenter la méthode loadUserByUsername().
	
// injection de dependances 
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    
    // Cette méthode est appelée par Spring Security pour récupérer les informations de l'utilisateur à partir de son nom d'utilisateur (dans ce cas, l'email).
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cherche l'utilisateur par email
        User user = userRepository.findByEmail(username);
        
        if (user == null) {
            // Si l'utilisateur n'est pas trouvé, on lève une exception
            throw new UsernameNotFoundException("User not found");
        }
        
        // Affiche le nom de l'utilisateur et son rôle dans la console
        System.out.println("User found: " + user.getName());
        System.out.println("User role: " + user.getRole());
        
        // Vérifie si le rôle contient "ROLE_" au début et crée une autorité
        // SimpleGrantedAuthority est utilisé pour encapsuler le rôle de l'utilisateur.
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        
        // Affiche l'autorité dans la console
        System.out.println("Granted Authority: " + authority.getAuthority());

        // Retourne un objet UserDetails avec les informations nécessaires pour l'authentification
        // On utilise le constructeur User de Spring Security pour retourner un objet UserDetails.
        // Collections.singletonList(authority) : Liste des autorités (ici, une seule autorité).
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(authority));
    }


}
