package nl.oudhoff.backendstephenking.service;

import nl.oudhoff.backendstephenking.dto.Input.BookInputDto;
import nl.oudhoff.backendstephenking.dto.Mapper.BookMapper;
import nl.oudhoff.backendstephenking.dto.Output.BookOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;


    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public BookOutputDto createBook(BookInputDto bookInputDto) {
        Book book = BookMapper.fromInputDtoToModel(bookInputDto);
        bookRepo.save(book);
        return BookMapper.fromModelToOutputDto(book);
    }

    public BookOutputDto updateBook(long id, BookInputDto bookInputDto) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            Book model = BookMapper.fromInputDtoToModel(bookInputDto);
            bookRepo.save(model);
            return BookMapper.fromModelToOutputDto(model);
        } else {
            throw new ResourceNotFoundException("Boek met id " + id + " niet gevonden");
        }
    }

    public BookOutputDto getBookById(long id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Boek met id " + id + " niet gevonden"));
        return BookMapper.fromModelToOutputDto(book);
    }

    public BookOutputDto getBookByTitle(String title) {
        Book book = bookRepo.findByTitleIgnoreCase(title).
                orElseThrow(() -> new ResourceNotFoundException("Boek met titel " + title + " niet gevonden"));

        return BookMapper.fromModelToOutputDto(book);
    }

    public List<BookOutputDto> getAllBooks() {
        List<Book> allBooks = bookRepo.findAll();
        List<BookOutputDto> booksOutputDtoList = new ArrayList<>();

        for (Book book : allBooks) {
            booksOutputDtoList.add(BookMapper.fromModelToOutputDto(book));
        }
        return booksOutputDtoList;
    }

    public void deleteBookById(long id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            bookRepo.delete(book.get());
        } else {
            throw new ResourceNotFoundException("Boek met id " + id + " niet gevonden");
        }
    }
}
