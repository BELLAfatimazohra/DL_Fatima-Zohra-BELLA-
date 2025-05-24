package com.example.registrationlogindemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.registrationlogindemo.entity.*;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
	
	// selecrionner tous les taches avec les users et les labels 
	
	
	// L'objectif de cette requête est de charger toutes les tâches (Task) avec leurs labels (Label), 
	//leurs entrées de texte (TextEntry) et les utilisateurs (User) associés à chaque entrée de texte.
    @Query("SELECT t FROM Task t " +
           "LEFT JOIN FETCH t.labels l " +
           "LEFT JOIN FETCH t.textEntries te " +
           "LEFT JOIN FETCH te.user u")
    List<Task> findAllWithLabelsAndUsers();
    
    // Sans FETCH, les labels seraient chargés de manière paresseuse (Lazy Loading), 
    // c'est-à-dire après la requête principale.
    // LEFT JOIN : Cela signifie que même si une tâche (Task) n'a pas de labels associés, elle sera quand même incluse dans le résultat.
    
}
