package nl.oudhoff.backendstephenking.controller;

import jakarta.validation.Valid;
import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.dto.dto.LoginRequestDto;
import nl.oudhoff.backendstephenking.dto.dto.LoginResponseDto;
import nl.oudhoff.backendstephenking.exception.BadRequestException;
import nl.oudhoff.backendstephenking.helper.BindingResultHelper;
import nl.oudhoff.backendstephenking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult bindingResult) {
        // Controleer op validatiefouten
        if (bindingResult.hasFieldErrors()) {
            // Gooien van een BadRequestException in plaats van ResourceNotFoundException
            throw new BadRequestException("Invalid input: " + BindingResultHelper.getErrorMessage(bindingResult));
        }

        UserOutputDto createUser = userService.createUser(userInputDto);

        // Maak een URI voor de aangemaakte gebruiker
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + userInputDto.getUsername()).toUriString());

        return ResponseEntity.created(uri).body(createUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        // Controleer op validatiefouten
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid login input: " + BindingResultHelper.getErrorMessage(bindingResult));
        }

        // Haal het token op
        String token = userService.loginUser(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        logger.info("User logged in: {}", loginRequestDto.getUsername());

        // Retourneer het token als onderdeel van de LoginResponseDto
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        // Haal alle gebruikers op en retourneer de lijst met DTO's
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        // Verwijder de gebruiker en geef een bevestigingsbericht
        userService.deleteUser(username);
        return ResponseEntity.ok("User with username " + username + " has been removed.");
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<?> getUserAuthorities(@PathVariable("username") String username) {
        // Haal de autoriteiten van de gebruiker op
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<?> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        // Controleer of het veld "authority" aanwezig is
        if (!fields.containsKey("authority")) {
            throw new BadRequestException("Missing authority field");
        }

        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException("Error adding authority: " + ex.getMessage());
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<?> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        // Verwijder de autoriteit van de gebruiker
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}
