package nl.oudhoff.backendstephenking.service;

import jakarta.annotation.Resource;
import nl.oudhoff.backendstephenking.dto.Input.BookInputDto;
import nl.oudhoff.backendstephenking.dto.Mapper.BookMapper;
import nl.oudhoff.backendstephenking.dto.Output.BookOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.model.Bookcover;
import nl.oudhoff.backendstephenking.repository.BookRepository;
import nl.oudhoff.backendstephenking.repository.FileUploadRepository;
import org.springframework.stereotype.Service;

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

    public Resource getBookcover(long id) {
        Optional<Book> optBook = bookRepo.findById(id);
        if(optBook .isEmpty()){
            throw new ResourceNotFoundException("Boek of boekcover niet gevonden");
        }
        Bookcover bookcover = optBook.get().getBookcover();
        if(bookcover == null){
            throw new ResourceNotFoundException("Boekcover niet gevonden");
        }
        return (Resource) bookcoverService.downLoadFile(bookcover.getFileName());
    }

    public Book addBookcoverToBook(String fileName, long id) {
        Optional<Book> optBook = bookRepo.findById(id);
        Optional<Bookcover> optBookcover = uploadRepository.findByFileName(fileName);

        if (optBook.isPresent() && optBookcover.isPresent()) {
            Bookcover bookcover = optBookcover.get();
            Book book = optBook.get();
            book.setBookcover(bookcover);
            return bookRepo.save(book);
        } else {
            throw new ResourceNotFoundException("Boek of boekcover niet gevonden");
        }
    }
}
