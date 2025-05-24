package com.example.registrationlogindemo.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.registrationlogindemo.entity.*;

@Repository
public interface TextEntryRepository extends JpaRepository<TextEntry, Long> {
	
	
	// recuperer les taches pas le id de user 
	// parce que le table text_entry contient task_id et user_id 
	// distinct
	@Query("SELECT DISTINCT t.task FROM TextEntry t WHERE t.user.id = :userId")
	List<Task> findTasksByUserId(@Param("userId") Long userId);
    // recuperer la liste des text_entry par taskId et userId
	List<TextEntry> findByUserIdAndTaskId(Long userId, Long taskId);
	List<TextEntry> findByTaskIdAndUserId(Long taskId, Long userId);
	
	
	// recuperer  la liste des text_entries pas num de ligne , le id de user et le id de task  
	public Optional<TextEntry> findByNumLigneAndUserIdAndTaskId(int numLigne, Long userId, Long taskId);

// compter le nombre de text_entry par le task_id et le user_id
	long countByTaskIdAndUserId(Long taskId, Long userId);
	
	
	long countByTaskId(Long taskId);
	
	// recuperer la liste des text_entry par task_id (admin)
	List<TextEntry> findByTaskId(Long taskId);
	
	
	@Query("""
		    SELECT te FROM TextEntry te
		    WHERE te.user.id = :userId
		    AND te.task.id = :taskId
		    AND te.id NOT IN (
		        SELECT a.textEntry.id FROM Assignment a
		    )
		""")
	// recuperer la liste des text_entry pas user id et task id et qui sont ne st pas annote , ca veut dire ne se trouve pas dans assigenement
    // ca veut dire id de text_entry n est pas dans table assignement
		List<TextEntry> findByUserIdAndTaskIdAndNotAnnotated(
		    @Param("userId") Long userId,
		    @Param("taskId") Long taskId
		);


}
