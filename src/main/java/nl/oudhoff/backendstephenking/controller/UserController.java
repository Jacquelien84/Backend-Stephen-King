package nl.oudhoff.backendstephenking.controller;

import nl.oudhoff.backendstephenking.dto.mapper.UserMapper;
import nl.oudhoff.backendstephenking.dto.output.UserOutputDto;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") String username) {
        UserOutputDto user = userService.get(username);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("fav/{id}/{bookId}")
    public ResponseEntity<UserOutputDto> addBookToFavorite(@PathVariable("id") String id, @PathVariable("bookId") Long bookId) throws Exception {
        Optional<User> user = userService.assignBookToFavourites(id, bookId);
        if (user.isPresent()) {
            UserOutputDto outputDto = UserMapper.fromModelToOutputDto(user.get());
            return ResponseEntity.ok().body(outputDto);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to add book to favourites");
    }

    @DeleteMapping("fav/{id}/{bookId}")
    public ResponseEntity<UserOutputDto> removeBookFromFavorite(@PathVariable("id") String id, @PathVariable("bookId") Long bookId) throws Exception {
        Optional<User> user = userService.removeBookFromFavourites(id, bookId);
        if (user.isPresent()) {
            UserOutputDto outputDto = UserMapper.fromModelToOutputDto(user.get());
            return ResponseEntity.ok().body(outputDto);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to remove book from favourites");
    }
}
