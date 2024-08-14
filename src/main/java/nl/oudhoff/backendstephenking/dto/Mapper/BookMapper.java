package nl.oudhoff.backendstephenking.dto.Mapper;

import nl.oudhoff.backendstephenking.dto.Input.BookInputDto;
import nl.oudhoff.backendstephenking.dto.Output.BookOutputDto;
import nl.oudhoff.backendstephenking.dto.Output.ReviewOutputDto;
import nl.oudhoff.backendstephenking.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static Book fromInputDtoToModel (BookInputDto inputDto){
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

    public static BookOutputDto fromModelToOutputDto (Book book) {
        BookOutputDto bookOutputDto = new BookOutputDto();
        bookOutputDto.setId(book.getId());
        bookOutputDto.setTitle(book.getTitle());
        bookOutputDto.setAuthor(book.getAuthor());
        bookOutputDto.setOriginalTitle(book.getOriginalTitle());
        bookOutputDto.setReleased(book.getReleased());
        bookOutputDto.setMovieAdaptation(book.getMovieAdaptation());
        bookOutputDto.setDescription(book.getDescription());

        List<ReviewOutputDto> reviewDtos = book.getListOfReviews().stream()
                .map(ReviewMapper::fromModelToOutputDto)
                .collect(Collectors.toList());
        bookOutputDto.setReviews(reviewDtos);

        return bookOutputDto;
    }

    public static List<BookOutputDto> bookModelListToOutputList(List<Book> books) {
        List<BookOutputDto> bookOutputDtoList = new ArrayList<>();
        // lambda
        books.forEach((book) -> bookOutputDtoList.add(fromModelToOutputDto(book)));
        return bookOutputDtoList;
    }
}
