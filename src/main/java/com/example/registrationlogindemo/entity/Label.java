package com.example.registrationlogindemo.entity;



import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "labels")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String labelName;

    // relation plusieurs-à-plusieurs entre Label et Task.
    // un label est dans plusieurs tache 
    // une tache a plusieurs labels 
    // la relation est déjà définie dans la classe Task.
    @ManyToMany(mappedBy = "labels")
    private List<Task> tasks;
 // Un label appartient à plusieurs tâches

    public Label() {}
    
    @Override
    public String toString() {
        return this.labelName; // ou le champ que tu veux afficher
    }


    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLabelName() { return labelName; }
    public void setLabelName(String labelName) { this.labelName = labelName; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}
