package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    // Requête pour récupérer tous les utilisateurs non supprimés
    List<User> findByIsDeletedFalse();
    
    // methode avec une requete personnalise
    // recuperer tous les users qui ont pas ce id 
    // c est pour le operation de supprimer (desafecter)
    @Query("SELECT u FROM User u WHERE u.id <> :userId AND u.role = 'ROLE_USER' AND u.isDeleted = false")
    List<User> findAllExcept(@Param("userId") Long userId);


}
