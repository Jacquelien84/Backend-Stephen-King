package nl.oudhoff.backendstephenking.controller;

import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        Book book1 = new Book();
        book1.setTitle("Carrie");
        book1.setAuthor("Stephen King");
        book1.setOriginalTitle("Carrie");
        book1.setReleased(1974L);
        book1.setMovieAdaptation("Carrie - 1976, 2013");
        book1.setDescription("Het verhaal gaat over Carrie, een zeventienjarig meisje met telekinetische gaven. Ze is een buitenbeentje op school en wordt vaak gepest en vernederd, ook door haar dominante moeder. Maar ze ontdekt haar krachten en krijgt deze steeds meer onder controle. Op het schoolbal gaan haar klasgenoten te ver en neemt ze wraak...");
        bookRepository.save(book1);
    }

    @Test
    @DisplayName("Should retrieve a book by ID")
    @WithMockUser(username = "testuser", roles = "USER")
    void shouldRetrieveBookById() throws Exception {
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

