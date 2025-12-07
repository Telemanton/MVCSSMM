package mabelmonte.ssmm.mabelmonte;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Guarda una nueva reseña en la base de datos.
     * @param review La entidad Review a guardar.
     * @return La reseña guardada con su ID.
     */
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    /**
     * Obtiene todas las reseñas de la base de datos.
     * @return Una lista de todas las reseñas.
     */
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}