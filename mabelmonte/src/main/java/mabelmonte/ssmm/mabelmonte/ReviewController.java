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
     * Crea una nueva reseña.
     * El cuerpo de la petición debe contener: 
     * { "nombreUsuario": "UsuarioX", "rating": 4.5, "comment": "..." }
     */
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, Object> reviewData) {
        try {
            // Extraer datos
            float rating = Float.parseFloat(reviewData.get("rating").toString());
            String comment = (String) reviewData.get("comment");
            

            String nombreUsuario = (String) reviewData.get("nombreUsuario");

            // Validar que el nombre de usuario no esté vacío
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("error", "El campo 'nombreUsuario' es obligatorio.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            

            Review newReview = new Review(nombreUsuario, rating, comment);
            
            Review savedReview = reviewService.save(newReview);
            
            // Respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("review_id", savedReview.getId());
            response.put("mensaje", "Reseña creada correctamente");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (NumberFormatException e) {
             Map<String, Object> error = new HashMap<>();
             error.put("success", false);
             error.put("error", "Formato de 'rating' incorrecto.");
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", "Error creando reseña: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // El método GET no necesita cambios.
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