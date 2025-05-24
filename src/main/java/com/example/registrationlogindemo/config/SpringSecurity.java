package com.example.registrationlogindemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
// Active la sécurité Web dans Spring.
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    //  Interface utilisée pour charger les informations d'utilisateur à partir de la base de données.
    private UserDetailsService userDetailsService;

    
    
    @Bean
    // Interface de Spring Security pour le cryptage des mots de passe.
    public static PasswordEncoder passwordEncoder() {
    	// Encodeur de mot de passe utilisant l'algorithme BCrypt
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Chaîne de filtres de sécurité (nouvelle manière de configurer la sécurité dans Spring Security).
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	// csrf().disable() : Désactive la protection CSRF (Cross-Site Request Forgery).
        http.csrf().disable()
// authorizeHttpRequests() : Gère l'autorisation des requêtes HTTP.
                .authorizeHttpRequests((authorize) ->
                // /register/**	Accessible à tout le monde
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasRole("USER")
                                .requestMatchers("/home").permitAll()
                // Configuration du formulaire de connexion :
                ).formLogin(
                        form -> form
                        // loginPage("/login") : Spécifie la page de connexion personnalisée.
                                .loginPage("/login")
                              // loginProcessingUrl("/login") : URL pour traiter le formulaire de connexion.
                                .loginProcessingUrl("/login")
                                // defaultSuccessUrl("/home", true) : Après connexion réussie, l'utilisateur est redirigé vers /home.
                                .defaultSuccessUrl("/home", true)  // Redirection après succès de la connexion
                                // les autres routes sont accessibles a autre 
                                .permitAll()
                // Configuration du logout :         
                ).logout(
                        logout -> logout
                        // logoutRequestMatcher() : URL pour le logout.
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                // permitAll() : Accessible à tous, même sans être connecté.
                                .permitAll()
                );
        // Construit et retourne la chaîne de filtres configurée.
        return http.build();
    }
// Configurer le gestionnaire d'authentification.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        // userDetailsService() : Utilise notre service CustomUserDetailsService.
                .userDetailsService(userDetailsService)
                // passwordEncoder() : Utilise l'encodeur BCryptPasswordEncoder.
                .passwordEncoder(passwordEncoder());
    }
}
