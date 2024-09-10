package nl.oudhoff.backendstephenking.controller;

import nl.oudhoff.backendstephenking.dto.Output.AuthOutputDto;
import nl.oudhoff.backendstephenking.dto.dto.LoginRequestDto;
import nl.oudhoff.backendstephenking.dto.dto.LoginResponseDto;
import nl.oudhoff.backendstephenking.service.AuthenticationService;
import nl.oudhoff.backendstephenking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
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
            String token = jwtUtil.generateToken(ud);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated");
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
        @PostMapping("/login")
        public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
            LoginResponseDto response = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
    }
}