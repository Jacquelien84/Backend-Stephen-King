package nl.oudhoff.backendstephenking.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import nl.oudhoff.backendstephenking.dto.Input.BookInputDto;
import nl.oudhoff.backendstephenking.dto.Output.BookOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.helper.BindingResultHelper;
import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.service.BookService;
import nl.oudhoff.backendstephenking.service.BookcoverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookcoverService bookcoverService;

    public BookController(BookService bookService, BookcoverService bookcoverService) {

        this.bookService = bookService;
        this.bookcoverService = bookcoverService;
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

    @PostMapping("/{id}/bookcovers")
    public ResponseEntity<Book> addBookcoverToBook(@PathVariable("id") Long id, @RequestBody MultipartFile file) throws IOException {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/books/")
                .path(id.toString())
                .path("/bookcovers")
                .toUriString();
        String fileName = bookcoverService.storeFile(file);
        Book book = bookService.addBookcoverToBook(fileName, id);
        return ResponseEntity.created(URI.create(url)).body(book);
    }

    @GetMapping("{id}/bookcovers")
    public ResponseEntity<Resource> getBookcover(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(bookService.getBookcover(id));
    }
}

