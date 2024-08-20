package nl.oudhoff.backendstephenking.controller;

import nl.oudhoff.backendstephenking.dto.Output.BookOutputDto;
import nl.oudhoff.backendstephenking.security.JwtService;
import nl.oudhoff.backendstephenking.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(BookController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = true)
public class OrderControllerUnitTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    BookService bookService;

    @Test
    @WithMockUser(username="testuser", roles="USER")
    void shouldRetrieveBook() throws Exception {
        BookOutputDto bookDto = new BookOutputDto();
        bookDto.setId(1L);
        bookDto.setTitle("Carrie");
        bookDto.setAuthor("Stephen King");
        bookDto.setOriginalTitle("Carrie");
        bookDto.setReleased(1974L);
        bookDto.setMovieAdaptation("Carrie - 1976, 2013");
        bookDto.setDescription("Het verhaal gaat over Carrie, een zeventienjarig meisje met telekinetische gaven. Ze is een buitenbeentje op school en wordt vaak gepest en vernederd, ook door haar dominante moeder. Maar ze ontdekt haar krachten en krijgt deze steeds meer onder controle. Op het schoolbal gaan haar klasgenoten te ver en neemt ze wraak...");

        Mockito.when(bookService.getBookById(anyLong())).thenReturn(bookDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/books/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Carrie")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", is("Stephen King")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.originalTitle", is("Carrie")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.released", is(1974)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieAdaptation", is("Carrie - 1976, 2013")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Het verhaal gaat over Carrie, een zeventienjarig meisje met telekinetische gaven. Ze is een buitenbeentje op school en wordt vaak gepest en vernederd, ook door haar dominante moeder. Maar ze ontdekt haar krachten en krijgt deze steeds meer onder controle. Op het schoolbal gaan haar klasgenoten te ver en neemt ze wraak...")));
    }
}
