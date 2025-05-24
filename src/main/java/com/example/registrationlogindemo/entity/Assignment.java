package com.example.registrationlogindemo.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @ManyToOne : Plusieurs Assignment peuvent être attribués à un même utilisateur.
    @ManyToOne
  // @JoinColumn(name = "user_id") : Crée une colonne user_id dans assignments, qui est une clé étrangère pointant vers User.
    @JoinColumn(name = "user_id")
    private User user;

    // La ligne de texte à annoter
   // @OneToOne : Chaque Assignment est uniquement lié à une seule ligne de texte (TextEntry).
    @OneToOne
    // Crée une colonne text_entry_id dans assignments, qui est une clé étrangère pointant vers TextEntry
    @JoinColumn(name = "text_entry_id")
    private TextEntry textEntry;

    // Le label choisi pour cette annotation
    // @ManyToOne : Plusieurs Assignment peuvent être associés au même label.
    @ManyToOne
    @JoinColumn(name = "label_id")
    private Label label;

    private LocalDateTime annotatedAt;

    public Assignment() {
        this.annotatedAt = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public TextEntry getTextEntry() { return textEntry; }
    public void setTextEntry(TextEntry textEntry) { this.textEntry = textEntry; }

    public Label getLabel() { return label; }
    public void setLabel(Label label) { this.label = label; }

    public LocalDateTime getAnnotatedAt() { return annotatedAt; }
    public void setAnnotatedAt(LocalDateTime annotatedAt) { this.annotatedAt = annotatedAt; }
}
