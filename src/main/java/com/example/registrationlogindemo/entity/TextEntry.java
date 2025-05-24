package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "text_entries")
public class TextEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numLigne;

    private boolean assigned;

    private LocalDateTime createdAt;

    // plusieurs text_entry peuvent etre dans une seule tache 
    // donc , dans la table text_entry 
    // @ManyToOne : Plusieurs TextEntry peuvent être associés à une seule tâche (Task).
    @ManyToOne
    @JoinColumn(name = "task_id")
    //  Crée une colonne task_id dans la table text_entries, qui est une clé étrangère pointant vers Task.
    private Task task;

    
    //  plusieurs text_entry peut etre pour un user 
    //  Plusieurs TextEntry peuvent être associés à un seul utilisateur (User).
    @ManyToOne
    // Crée une colonne user_id dans text_entries, qui est une clé étrangère pointant vers User.
    @JoinColumn(name = "user_id") 
    private User user;

    public TextEntry() {
        this.createdAt = LocalDateTime.now();
        this.assigned = false;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumLigne() { return numLigne; }
    public void setNumLigne(int numLigne) { this.numLigne = numLigne; }

    public boolean isAssigned() { return assigned; }
    public void setAssigned(boolean assigned) { this.assigned = assigned; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public int getLineIndex() {
        return this.numLigne;
    }

}
