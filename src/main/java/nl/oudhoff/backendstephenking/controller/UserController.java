package nl.oudhoff.backendstephenking.controller;

import jakarta.validation.Valid;
import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.helper.BindingResultHelper;
import nl.oudhoff.backendstephenking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserInputDto userInputDto) {
        UserOutputDto createUser = userService.createUser(userInputDto);
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + userInputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body("Done");
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDto> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
