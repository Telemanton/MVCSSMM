package mabelmonte.ssmm.mabelmonte;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreUsuario;

    // Ya no hay relación con Usuario

    private float rating; // Valoración (e.g., 1.0 a 5.0)

    @Column(columnDefinition = "TEXT")
    private String comment; // El texto de la reseña
    
    // Ya no hay itemReviewed

    private LocalDateTime createdAt;
    
    // --- Constructores ---
    public Review() {
    }

    public Review(String nombreUsuario, float rating, String comment) {
        this.nombreUsuario = nombreUsuario;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }
    
    // --- Getters y Setters ---

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}