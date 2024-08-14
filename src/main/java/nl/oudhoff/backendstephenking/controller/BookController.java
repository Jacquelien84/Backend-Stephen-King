package nl.oudhoff.backendstephenking.controller;

import jakarta.validation.Valid;
import nl.oudhoff.backendstephenking.dto.Input.BookInputDto;
import nl.oudhoff.backendstephenking.dto.Output.BookOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.helper.BindingResultHelper;
import nl.oudhoff.backendstephenking.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> createBooks(@Valid @RequestBody BookInputDto bookInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResourceNotFoundException("Something went wrong, Please check the following fields. " + BindingResultHelper.getErrorMessage(bindingResult));
        }
        BookOutputDto createBook = bookService.createBook(bookInputDto);
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + bookInputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(createBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookOutputDto> updateBook(@PathVariable long id, @RequestBody BookInputDto bookInputDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookInputDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookOutputDto> getBookById(@PathVariable long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<BookOutputDto> getBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @GetMapping
    public ResponseEntity<List<BookOutputDto>> getAllBooks() {

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable long id) throws ResourceNotFoundException {
        bookService.deleteBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Boek met id " + id + " is verwijderd!");
    }
}
