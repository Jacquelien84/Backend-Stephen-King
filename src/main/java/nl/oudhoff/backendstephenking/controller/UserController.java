package nl.oudhoff.backendstephenking.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.dto.dto.LoginRequestDto;
import nl.oudhoff.backendstephenking.dto.dto.LoginResponseDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.helper.BindingResultHelper;
import nl.oudhoff.backendstephenking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResourceNotFoundException("Something went wrong, Please check the following fields. " + BindingResultHelper.getErrorMessage(bindingResult));
        }
        UserOutputDto createUser = userService.createUser(userInputDto);
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + userInputDto.getUsername()).toUriString());

        return ResponseEntity.created(uri).body(createUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        String token = userService.loginUser(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        logger.info("User logged in: {}", loginRequestDto.getUsername());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Transactional
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) throws ResourceNotFoundException {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body("User with username " + username + " has been removed.");
    }
}

