package nl.oudhoff.backendstephenking.repository;

import nl.oudhoff.backendstephenking.model.Bookcover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository  extends JpaRepository<Bookcover, String> {
    Optional<Bookcover> findByFileName(String fileName);
}

