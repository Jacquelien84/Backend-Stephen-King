package nl.oudhoff.backendstephenking.repository;

import nl.oudhoff.backendstephenking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, String> {
    Optional<User> findByUsernameIgnoreCase(String username);
    void deleteByUsernameIgnoreCase(String username);
}

