package nl.oudhoff.backendstephenking.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.oudhoff.backendstephenking.dto.input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.mapper.UserMapper;
import nl.oudhoff.backendstephenking.dto.output.AuthenticationResponse;
import nl.oudhoff.backendstephenking.exception.TokenGenerationFailedException;
import nl.oudhoff.backendstephenking.model.Token;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.repository.TokenRepository;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import nl.oudhoff.backendstephenking.security.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepo, JwtUtil jwtUtil, TokenRepository tokenRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserInputDto request) {
        User user = UserMapper.fromInputDtoToModel(request);

        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, null, "User already exist");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        user = userRepo.save(user);
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User registration was successful");
    }

    public AuthenticationResponse authenticate(UserInputDto request) {
        Optional<User> user;

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));

        try {
            user = userRepo.findByUsername(request.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        if (user.isPresent()) {
            try {
                String accessToken = jwtUtil.generateAccessToken(user.get());
                String refreshToken = jwtUtil.generateRefreshToken(user.get());

                revokeAllTokenByUser(user.get());
                saveUserToken(accessToken, refreshToken, user.get());

                return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");
            }
            catch(TokenGenerationFailedException e){
                throw new TokenGenerationFailedException(e.getCause());
            }
        }
        return null;
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepo.findAllAccessTokensByUser(user.getUsername());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> t.setLoggedOut(true));

        tokenRepo.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepo.save(token);
    }

    public ResponseEntity<Object> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        String username = jwtUtil.extractUsername(token);

        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new RuntimeException("No user found"));


        if(jwtUtil.isValidRefreshToken(token, user)) {

            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity<>(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

