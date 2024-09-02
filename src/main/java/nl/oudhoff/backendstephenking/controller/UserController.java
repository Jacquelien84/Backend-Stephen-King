package nl.oudhoff.backendstephenking.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.helper.BindingResultHelper;
import nl.oudhoff.backendstephenking.service.UserService;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Transactional
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) throws ResourceNotFoundException {
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body("User with username " + username + " has been removed.");
    }
}
