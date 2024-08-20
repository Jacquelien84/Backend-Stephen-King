package nl.oudhoff.backendstephenking.service;

import nl.oudhoff.backendstephenking.dto.Input.BookInputDto;
import nl.oudhoff.backendstephenking.dto.Output.BookOutputDto;
import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepo;

    @InjectMocks
    BookService bookService;

    BookInputDto bookInputDto1;
    BookInputDto bookInputDto2;
    BookInputDto bookInputDto3;
    Book book1;
    Book book2;
    Book book3;


    @BeforeEach
    void setUp() {
        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Carrie");
        book1.setAuthor("Stephen King");
        book1.setOriginalTitle("Carrie");
        book1.setReleased(1974L);
        book1.setMovieAdaptation("Carrie - 1976, 2013");
        book1.setDescription("Het verhaal gaat over Carrie, een zeventienjarig meisje met telekinetische gaven. Ze is een buitenbeentje op school en wordt vaak gepest en vernederd, ook door haar dominante moeder. Maar ze ontdekt haar krachten en krijgt deze steeds meer onder controle. Op het schoolbal gaan haar klasgenoten te ver en neemt ze wraak...");

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Bezeten Stad");
        book2.setAuthor("Stephen King");
        book2.setOriginalTitle("Bezeten Stad");
        book2.setReleased(1975L);
        book2.setMovieAdaptation("Salems Lot - 2004");
        book2.setDescription("Ben Mears was pas zeven jaar toen hij zijn geboortestadje 'Salem's Lot' verliet. Nu, vijfentwintig jaar later, is hij teruggekomen om de waarheid bloot te leggen. De waarheid over de geheimzinnige verdwijningen, de spookverhalen en de geestesverschijningen. Maar niemand wist dat er ook een vreemdeling was gekomen, iemand die een geheim met zich droeg dat zo oud was als de wereld. Zij die hij aanraakte zouden sterven, met al degenen die zij liefhebben. Alles zou voor altijd veranderen. Voor Susan, die niet beschermd kon worden door haar liefde voor Ben. Voor Vader Callahan, de priester die zijn geloof aan een laatste proef onderwierp. En voor Mark, een kleine jongen die zijn fantasiewereld werkelijkheid ziet worden. Toch blijkt juist hij het beste in staat de huiveringwekkende nachtmerrie van 'Salem's Lot' het hoofd te bieden.");

        book3 = new Book();
        book3.setId(3L);
        book3.setTitle("De shining");
        book3.setAuthor("Stephen King");
        book3.setOriginalTitle("De shining");
        book3.setReleased(1977L);
        book3.setMovieAdaptation("The Shining - 1980, 1997");
        book3.setDescription("Het verhaal speelt zich af in het verlaten Overlook Hotel ergens in de Rocky Mountains, waar schrijver Jack Torrance, zijn vrouw Wendy en hun zoontje Danny huismeesters zijn. Danny is helderziend en voorziet het gevaar, de spoken van het verleden komen in het hotel opnieuw tot leven. Jack merkt dit ook en het hotel drijft hem tot waanzin. Dit leidt ertoe dat Wendy en Danny, omsloten door sneeuw, ver van de buitenwereld, opgesloten zitten met een levensgevaarlijke gek.");

        bookInputDto1 = new BookInputDto();
        bookInputDto1.setId(1L);
        bookInputDto1.setTitle("Carrie");
        bookInputDto1.setAuthor("Stephen King");
        bookInputDto1.setOriginalTitle("Carrie");
        bookInputDto1.setReleased(1974L);
        bookInputDto1.setMovieAdaptation("Carrie - 1976, 2013");
        bookInputDto1.setDescription("Het verhaal gaat over Carrie, een zeventienjarig meisje met telekinetische gaven. Ze is een buitenbeentje op school en wordt vaak gepest en vernederd, ook door haar dominante moeder. Maar ze ontdekt haar krachten en krijgt deze steeds meer onder controle. Op het schoolbal gaan haar klasgenoten te ver en neemt ze wraak...");

        bookInputDto2 = new BookInputDto();
        bookInputDto2.setId(2L);
        bookInputDto2.setTitle("Bezeten Stad");
        bookInputDto2.setAuthor("Stephen King");
        bookInputDto2.setOriginalTitle("Bezeten Stad");
        bookInputDto2.setReleased(1975L);
        bookInputDto2.setMovieAdaptation("Salems Lot - 2004");
        bookInputDto2.setDescription("Ben Mears was pas zeven jaar toen hij zijn geboortestadje 'Salem's Lot' verliet. Nu, vijfentwintig jaar later, is hij teruggekomen om de waarheid bloot te leggen. De waarheid over de geheimzinnige verdwijningen, de spookverhalen en de geestesverschijningen. Maar niemand wist dat er ook een vreemdeling was gekomen, iemand die een geheim met zich droeg dat zo oud was als de wereld. Zij die hij aanraakte zouden sterven, met al degenen die zij liefhebben. Alles zou voor altijd veranderen. Voor Susan, die niet beschermd kon worden door haar liefde voor Ben. Voor Vader Callahan, de priester die zijn geloof aan een laatste proef onderwierp. En voor Mark, een kleine jongen die zijn fantasiewereld werkelijkheid ziet worden. Toch blijkt juist hij het beste in staat de huiveringwekkende nachtmerrie van 'Salem's Lot' het hoofd te bieden.");

        bookInputDto3 = new BookInputDto();
        bookInputDto3.setId(3L);
        bookInputDto3.setTitle("De shining");
        bookInputDto3.setAuthor("Stephen King");
        bookInputDto3.setOriginalTitle("De shining");
        bookInputDto3.setReleased(1977L);
        bookInputDto3.setMovieAdaptation("The Shining - 1980, 1997");
        bookInputDto3.setDescription("Het verhaal speelt zich af in het verlaten Overlook Hotel ergens in de Rocky Mountains, waar schrijver Jack Torrance, zijn vrouw Wendy en hun zoontje Danny huismeesters zijn. Danny is helderziend en voorziet het gevaar, de spoken van het verleden komen in het hotel opnieuw tot leven. Jack merkt dit ook en het hotel drijft hem tot waanzin. Dit leidt ertoe dat Wendy en Danny, omsloten door sneeuw, ver van de buitenwereld, opgesloten zitten met een levensgevaarlijke gek.");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should create a book")
    void createBook() {

        // Arrange
        when(bookRepo.save(any(Book.class))).thenReturn(book2);

        // Act
        BookOutputDto bookOutputDto = bookService.createBook(bookInputDto2);

        // Assert
        assertEquals("Bezeten Stad", bookOutputDto.getTitle());
        assertEquals("Stephen King", bookOutputDto.getAuthor());
        assertEquals("Bezeten Stad", bookOutputDto.getOriginalTitle());
        assertEquals(1975L, bookOutputDto.getReleased());
        assertEquals("Salems Lot - 2004", bookOutputDto.getMovieAdaptation());
        assertEquals("Ben Mears was pas zeven jaar toen hij zijn geboortestadje 'Salem's Lot' verliet. Nu, vijfentwintig jaar later, is hij teruggekomen om de waarheid bloot te leggen. De waarheid over de geheimzinnige verdwijningen, de spookverhalen en de geestesverschijningen. Maar niemand wist dat er ook een vreemdeling was gekomen, iemand die een geheim met zich droeg dat zo oud was als de wereld. Zij die hij aanraakte zouden sterven, met al degenen die zij liefhebben. Alles zou voor altijd veranderen. Voor Susan, die niet beschermd kon worden door haar liefde voor Ben. Voor Vader Callahan, de priester die zijn geloof aan een laatste proef onderwierp. En voor Mark, een kleine jongen die zijn fantasiewereld werkelijkheid ziet worden. Toch blijkt juist hij het beste in staat de huiveringwekkende nachtmerrie van 'Salem's Lot' het hoofd te bieden.", bookOutputDto.getDescription());
    }

    @Test
    @DisplayName("Should update the information of a book")
    void updateBook() {

        // Arrange
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book3));

        // Act
        BookOutputDto bookOutputDto = bookService.updateBook(3L, bookInputDto3);

        // Assert
        assertEquals("De shining", book3.getTitle());
        assertEquals("Stephen King", book3.getAuthor());
        assertEquals("De shining", book3.getOriginalTitle());
        assertEquals(1977L, book3.getReleased());
        assertEquals("The Shining - 1980, 1997", book3.getMovieAdaptation());
        assertEquals("Het verhaal speelt zich af in het verlaten Overlook Hotel ergens in de Rocky Mountains, waar schrijver Jack Torrance, zijn vrouw Wendy en hun zoontje Danny huismeesters zijn. Danny is helderziend en voorziet het gevaar, de spoken van het verleden komen in het hotel opnieuw tot leven. Jack merkt dit ook en het hotel drijft hem tot waanzin. Dit leidt ertoe dat Wendy en Danny, omsloten door sneeuw, ver van de buitenwereld, opgesloten zitten met een levensgevaarlijke gek.", book3.getDescription());
        }

    @Test
    @DisplayName("Should find a book by it's id")
    void getBookById() {

        // Arrange
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book1));

        // Act
        BookOutputDto bookOutputDto = bookService.getBookById(1);

        // Assert
        assertEquals(1L, bookOutputDto.getId());
        assertEquals("Carrie", bookOutputDto.getTitle());
        assertEquals("Stephen King", bookOutputDto.getAuthor());
        assertEquals("Carrie", bookOutputDto.getOriginalTitle());
        assertEquals(1974, bookOutputDto.getReleased());
        assertEquals("Carrie - 1976, 2013", bookOutputDto.getMovieAdaptation());
        assertEquals("Het verhaal gaat over Carrie, een zeventienjarig meisje met telekinetische gaven. Ze is een buitenbeentje op school en wordt vaak gepest en vernederd, ook door haar dominante moeder. Maar ze ontdekt haar krachten en krijgt deze steeds meer onder controle. Op het schoolbal gaan haar klasgenoten te ver en neemt ze wraak...", bookOutputDto.getDescription() );
    }

    @Test
    @DisplayName("Should find a book by it's title")
    void getBookByTitle() {

        // Arrange
        when(bookRepo.findByTitleIgnoreCase(anyString())).thenReturn(Optional.of(book1));

        // Act
        BookOutputDto bookOutputDto = bookService.getBookByTitle("Carrie");

        // Assert
        assertEquals("Carrie", bookOutputDto.getTitle());
        verify(bookRepo).findByTitleIgnoreCase("Carrie");
    }

    @Test
    @DisplayName("Should get all books")
    void getAllBooks() {

        // Arrange
        when(bookRepo.findAll()).thenReturn(List.of(book1, book2, book3));

        // Act
        List<BookOutputDto> booksFound = bookService.getAllBooks();

        // Assert
        assertEquals("Carrie", booksFound.get(0).getTitle());
        assertEquals("Bezeten Stad", booksFound.get(1).getTitle());
        assertEquals("De shining", booksFound.get(2).getTitle());
    }

    @Test
    @DisplayName("Should delete a book")
    void deleteBookById() {

        // Arrange
        when(bookRepo.findById(3L)).thenReturn(Optional.of(book3));

        // Act
        bookService.deleteBookById(3L);

        // Assert
        verify(bookRepo).delete(book3);
    }
}