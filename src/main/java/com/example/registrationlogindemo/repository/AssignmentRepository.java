package com.example.registrationlogindemo.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.registrationlogindemo.entity.Assignment;
import com.example.registrationlogindemo.entity.TextEntry;
import com.example.registrationlogindemo.entity.User;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	
	
	// recuperer la liste des assignements par user id et text_entry
	Optional<Assignment> findByUserAndTextEntry(User user, TextEntry textEntry);
	
	// (Optionnel) Cette méthode permet de récupérer toutes les Assignments d’un utilisateur sur une liste de TextEntry
	// une liste de text_entry et un user 
    List<Assignment> findByTextEntryIdInAndUserId(List<Long> textEntryIds, Long userId);

    
    // recupere le nombre de text_entry assigne depuis assignement par user id et task id 
    // task id et le id de task de text_entry
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.user.id = :userId AND a.textEntry.task.id = :taskId")
    long countByTaskIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);
    
    @Query("SELECT COUNT(av) FROM Assignment av WHERE av.user.id = :userId AND av.textEntry.task.id = :taskId")
	long countValidatedEntriesForTask(@Param("userId") Long userId, @Param("taskId") Long taskId);
	
    
    // recupere le nombre de assignemtn pour une task 
	 long countByTextEntry_TaskId(Long taskId);
	 
	 
	 // recupere tous les assignemnt pour un user id et une task id de text_entry 
	 @Query("SELECT a FROM Assignment a WHERE a.user.id = :userId AND a.textEntry.task.id = :taskId")
	 List<Assignment> findByUserIdAndTaskId(@Param("userId") Long userId, @Param("taskId") Long taskId);

	 
	 // recupere une assigenement pour une text_entry 
	 // parce que text_entry id se trouve dans la table assigenemtn 
	 Optional<Assignment> findByTextEntry(TextEntry textEntry);


}
