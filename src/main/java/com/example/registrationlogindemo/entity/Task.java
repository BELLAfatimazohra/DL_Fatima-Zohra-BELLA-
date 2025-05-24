package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
// table tasks
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String csvFilePath;

    private LocalDateTime createdAt;

   
    @Column(name = "date_limite")
    private LocalDateTime dateLimite;

    
    // une task / dataset peut avoir plusieurs labels 
    @ManyToMany
    // donc il y a un table de jointure 
    // ce table . contient task_id et label_id
    @JoinTable(
        name = "task_labels",
        joinColumns = @JoinColumn(name = "task_id"),
        // inverseJoinColumns , ca veut dire l'autre table 
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    // une tache a une list des labels 
    private Set<Label> labels;

    // chaque task a plusieurs text_entry
    // Spécifie que la relation est définie par l'attribut task dans la classe TextEntry.
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TextEntry> textEntries;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

 
    public LocalDateTime getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(LocalDateTime dateLimite) {
        this.dateLimite = dateLimite;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public Set<TextEntry> getTextEntries() {
        return textEntries;
    }

    public void setTextEntries(Set<TextEntry> textEntries) {
        this.textEntries = textEntries;
    }

	
}
