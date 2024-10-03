package nl.oudhoff.backendstephenking.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.oudhoff.backendstephenking.model.Token;
import nl.oudhoff.backendstephenking.repository.TokenRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepo;

    public CustomLogoutHandler(TokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        Token storedToken = tokenRepo.findByAccessToken(token).orElse(null);

        if(storedToken != null) {
            storedToken.setLoggedOut(true);
            tokenRepo.save(storedToken);
        }
    }
}
