package nl.oudhoff.backendstephenking.repository;

import nl.oudhoff.backendstephenking.model.Bookcover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookcoverRepository extends JpaRepository<Bookcover, String> {
}
