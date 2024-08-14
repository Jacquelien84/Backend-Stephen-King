package nl.oudhoff.backendstephenking.dto.Mapper;

import nl.oudhoff.backendstephenking.dto.InputDto.BookInputDto;
import nl.oudhoff.backendstephenking.dto.OutputDto.BookOutputDto;
import nl.oudhoff.backendstephenking.model.Book;

public class BookMapper {

    public static Book fromInputDtoToModel(BookInputDto inputDto) {
        Book b = new Book();
        b.setId(inputDto.getId());
        b.setTitle(inputDto.getTitle());
        b.setAuthor(inputDto.getAuthor());
        b.setOriginalTitle(inputDto.getOriginalTitle());
        b.setReleased(inputDto.getReleased());
        b.setMovieAdaptation(inputDto.getMovieAdaptation());
        b.setDescription(inputDto.getDescription());
        return b;
    }

    public static BookOutputDto fromModelToOutputDto(Book book) {
        BookOutputDto bookOutputDto = new BookOutputDto();
        bookOutputDto.setId(book.getId());
        bookOutputDto.setTitle(book.getTitle());
        bookOutputDto.setAuthor(book.getAuthor());
        bookOutputDto.setOriginalTitle(book.getOriginalTitle());
        bookOutputDto.setReleased(book.getReleased());
        bookOutputDto.setMovieAdaptation(book.getMovieAdaptation());
        bookOutputDto.setDescription(book.getDescription());
        return bookOutputDto;
    }
}
