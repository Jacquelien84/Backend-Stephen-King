package nl.oudhoff.backendstephenking.repository;

import nl.oudhoff.backendstephenking.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(long id);
    Optional<Review> findByNameIgnoreCase(String name);
}
