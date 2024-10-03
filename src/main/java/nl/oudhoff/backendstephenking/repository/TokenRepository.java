package nl.oudhoff.backendstephenking.repository;

import nl.oudhoff.backendstephenking.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("select t from Token t inner join User u on t.user.username = u.username where t.user.username = :username and t.loggedOut = false")
    List<Token> findAllAccessTokensByUser(String username);

    Optional<Token> findByAccessToken(String token);

    Optional<Token > findByRefreshToken(String token);
}

