package nl.oudhoff.backendstephenking.service;

import jakarta.transaction.Transactional;
import nl.oudhoff.backendstephenking.dto.input.BookInputDto;
import nl.oudhoff.backendstephenking.dto.mapper.BookMapper;
import nl.oudhoff.backendstephenking.dto.output.BookOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.model.Bookcover;
import nl.oudhoff.backendstephenking.repository.BookRepository;
import nl.oudhoff.backendstephenking.repository.FileUploadRepository;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final FileUploadRepository uploadRepository;
    private final BookcoverService bookcoverService;

    public BookService(BookRepository bookRepo, FileUploadRepository uploadRepository, BookcoverService bookcoverService) {

        this.bookRepo = bookRepo;
        this.uploadRepository = uploadRepository;
        this.bookcoverService = bookcoverService;
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
        Book book = bookRepo.findByTitleContainingIgnoreCase(title).
                orElseThrow(() -> new ResourceNotFoundException("Book with titel " + title + " not found"));

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

    @Transactional
    public void deleteBookById(long id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            bookRepo.delete(book.get());
        } else {
            throw new ResourceNotFoundException("Book with id " + id + " not found");
        }
    }

    public Book addBookcoverToBook(String fileName, long id) {
        Optional<Book> optBook = bookRepo.findById(id);
        Optional<Bookcover> optBookcover = uploadRepository.findById(fileName);

        if (optBook.isPresent() && optBookcover.isPresent()) {
            Bookcover bookcover = optBookcover.get();
            Book book = optBook.get();
            book.setBookcover(bookcover);
            return bookRepo.save(book);
        } else {
            throw new ResourceNotFoundException("Book or bookcover not found");
        }
    }

    public Resource getBookcover(long id) {
        Optional<Book> optBook = bookRepo.findById(id);
        if (optBook.isEmpty()) {
            throw new ResourceNotFoundException("Book or bookcover not found");
        }
        Bookcover bookcover = optBook.get().getBookcover();
        if (bookcover == null) {
            throw new ResourceNotFoundException("Bookcover not found");
        }
        return bookcoverService.downLoadFile(bookcover.getFileName());
    }
}













