package nl.oudhoff.backendstephenking.repository;

import nl.oudhoff.backendstephenking.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleIgnoreCase(String title);
}
