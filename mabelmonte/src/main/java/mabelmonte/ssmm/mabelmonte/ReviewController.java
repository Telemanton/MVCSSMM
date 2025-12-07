package mabelmonte.ssmm.mabelmonte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * POST /api/reviews
     * Crea una nueva reseña (comentario y puntuación).
     * El cuerpo de la petición debe contener: { "rating": 4.5, "comment": "..." }
     */
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, Object> reviewData) {
        try {
            // Extraer datos
            float rating = Float.parseFloat(reviewData.get("rating").toString());
            String comment = (String) reviewData.get("comment");
            
            // Creación y guardado de la entidad
            Review newReview = new Review(rating, comment);
            Review savedReview = reviewService.save(newReview);
            
            // Respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("review_id", savedReview.getId());
            response.put("mensaje", "Reseña creada correctamente");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", "Error creando reseña: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * GET /api/reviews
     * Obtiene todas las reseñas.
     */
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        try {
            List<Review> reviews = reviewService.findAll();
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}