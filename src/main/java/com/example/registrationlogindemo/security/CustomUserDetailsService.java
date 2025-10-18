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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // ✅ Ajoute le préfixe "ROLE_" si ce n’est pas déjà présent
        String roleName = user.getRole().startsWith("ROLE_") ? user.getRole() : "ROLE_" + user.getRole();
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }


}
