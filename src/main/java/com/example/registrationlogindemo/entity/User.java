package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// cette classe est une entité JPA 
@Entity
// @Table(name = "users") : Spécifie le nom de la table dans la base de données.

@Table(name = "users")
public class User {
  
// @GeneratedValue(strategy = GenerationType.IDENTITY) : L'ID est auto-généré par la base de données (auto-incrément).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Ici, nous utilisons une simple colonne pour stocker le rôle
    @Column(nullable = false)
    private String role; // Le rôle sera simplement un attribut String (ex : "ROLE_USER", "ROLE_ADMIN")

    // Ajout du champ isDeleted pour la suppression logique
    @Column(nullable = false)
    private boolean isDeleted = false; // Par défaut, un utilisateur n'est pas supprimé

    public User() {}

    public User(Long id, String name, String email, String password, String role, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }
}
