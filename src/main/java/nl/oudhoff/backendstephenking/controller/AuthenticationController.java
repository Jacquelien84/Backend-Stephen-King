package nl.oudhoff.backendstephenking.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.oudhoff.backendstephenking.dto.input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.output.AuthOutputDto;
import nl.oudhoff.backendstephenking.dto.output.AuthenticationResponse;
import nl.oudhoff.backendstephenking.exception.AuthenticationFailedException;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.security.JwtUtil;
import nl.oudhoff.backendstephenking.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationService authService, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> signIn(@RequestBody AuthOutputDto authDto) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(authDto.username, authDto.password);

        try {
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateAccessToken((User) ud);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated");
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserInputDto request ) throws Exception {
        try {
            return ResponseEntity.ok(authService.register(request));
        }
        catch(AuthenticationFailedException e) {
            throw new AuthenticationFailedException(e.getCause());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserInputDto request) throws AuthenticationFailedException {

        try {
            return ResponseEntity.ok(authService.authenticate(request));
        }
        catch(AuthenticationFailedException e) {
            throw new AuthenticationFailedException(e.getCause());
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request, HttpServletResponse response) throws AuthenticationFailedException {
        try {
            return ResponseEntity.ok(authService.refreshToken(request, response));
        }
        catch(AuthenticationFailedException e) {
            throw new AuthenticationFailedException(e.getCause());
        }
    }
}