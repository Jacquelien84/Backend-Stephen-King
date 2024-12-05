package nl.oudhoff.backendstephenking.controller;

import nl.oudhoff.backendstephenking.dto.input.BookInputDto;
import nl.oudhoff.backendstephenking.dto.output.BookOutputDto;
import nl.oudhoff.backendstephenking.repository.BookRepository;
import nl.oudhoff.backendstephenking.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class BookControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepo;

    private BookInputDto bookInputDto;

    @Test
    @Order(1)
    void shouldCreateBook() throws Exception {

        String requestJson = """
                {
                "id": 8,
                "title": "Cujo",
                "author": "Stephen King",
                "originalTitle": "Cujo",
                "released": 1981,
                "movieAdaptation": "Cujo - 1983",
                "description": "Stel je een reusachtige Sint Bernard voor. Een grote lobbes en de beste vriend van de tienjarige Brett Camber. Helaas komt hier verandering in wanneer Cujo in aanraking komt met een ernstig zieke vleermuis. Door de hondsdolheid die hij hierdoor oploopt is niets of niemand meer veilig."
                }
                """;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");
        assertThat(locationHeader, matchesPattern("^.*/books/\\d+$"));
    }

    @Test
    @Order(2)
    void shouldGetBookById() throws Exception {
        BookOutputDto createdBook = bookService.createBook(bookInputDto);

        mockMvc.perform(get("/books/{id}", createdBook.getId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdBook.getId()))
                .andExpect(jsonPath("$.name").value("simulation"));
    }
}

