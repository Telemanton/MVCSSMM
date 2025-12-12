package mabelmonte.ssmm.mabelmonte;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // Método para obtener todas las reseñas por valoración
    List<Review> findByRating(float rating);
    
}