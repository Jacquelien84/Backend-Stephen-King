package nl.oudhoff.backendstephenking.repository;

import nl.oudhoff.backendstephenking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findById(long id);
}

