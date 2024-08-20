package nl.oudhoff.backendstephenking.service;

import nl.oudhoff.backendstephenking.dto.Input.ReviewInputDto;
import nl.oudhoff.backendstephenking.dto.Output.ReviewOutputDto;
import nl.oudhoff.backendstephenking.model.Review;
import nl.oudhoff.backendstephenking.repository.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepo;

    @InjectMocks
    ReviewService reviewService;

    ReviewInputDto reviewInputDto1;
    ReviewInputDto reviewInputDto2;
    Review review1;
    Review review2;

    @BeforeEach
    void setUp() {
        review1 = new Review();
        review1.setId(1L);
        review1.setName("Jacquelien");
        review1.setReviewDate(LocalDate.ofEpochDay(2024- 1 - 1));
        review1.setReviewText("Heel leuk boek");

        review2 = new Review();
        review2.setId(2L);
        review2.setName("Karel");
        review2.setReviewDate(LocalDate.ofEpochDay(2024- 2 - 1));
        review2.setReviewText("Was leuk");

        reviewInputDto1 = new ReviewInputDto();
        reviewInputDto1.setId(1L);
        reviewInputDto1.setName("Jacquelien");
        reviewInputDto1.setReviewDate(LocalDate.ofEpochDay(2024- 1 - 1));
        reviewInputDto1.setReviewText("Heel leuk boek");

        reviewInputDto2 = new ReviewInputDto();
        reviewInputDto2.setId(2L);
        reviewInputDto2.setName("Karel");
        reviewInputDto2.setReviewDate(LocalDate.ofEpochDay(2024- 2 - 1));
        reviewInputDto2.setReviewText("Was leuk");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should create a review")
    void createReview() {

        // Arrange
        when(reviewRepo.save(any(Review.class))).thenReturn(review1);

        // Act
        ReviewOutputDto review1 = reviewService.createReview(reviewInputDto1);

        // Assert
        assertEquals(1L, review1.getId());
        assertEquals("Jacquelien", review1.getName());
        assertEquals(LocalDate.ofEpochDay (2024- 1 - 1), review1.getReviewDate());
        assertEquals("Heel leuk boek", review1.getReviewText());
    }

    @Test
    @DisplayName("Should update the review")
    void updateReview() {

        // Arrange
        when(reviewRepo.findById(anyLong())).thenReturn(Optional.of(review2));

        // Act
        ReviewOutputDto review2 = reviewService.updateReview(2L, reviewInputDto2);

        // Assert
        assertEquals(2L, review2.getId());
        assertEquals("Karel", review2.getName());
        assertEquals(LocalDate.ofEpochDay (2024- 2 - 1), review2.getReviewDate());
        assertEquals("Was leuk", review2.getReviewText());
    }

    @Test
    @DisplayName("Should find a review by id")
    void getReviewById() {

        // Arrange
        when(reviewRepo.findById(anyLong())).thenReturn(Optional.of(review2));

        // Act
        ReviewOutputDto reviewOutputDto = reviewService.getReviewById(2L);

        // Assert
        assertEquals(2L, reviewOutputDto.getId());
        assertEquals("Karel", reviewOutputDto.getName());
        assertEquals(LocalDate.ofEpochDay (2024- 2 - 1), reviewOutputDto.getReviewDate());
        assertEquals("Was leuk", reviewOutputDto.getReviewText());
    }

    @Test
    @DisplayName("Should find all reviews")
    void getAllReviews() {

        // Arrange
        when(reviewRepo.findAll()).thenReturn(List.of(review1, review2));

        // Act
        List<ReviewOutputDto> reviewsFound = reviewService.getAllReviews();

        // Assert
        assertEquals(1, reviewsFound.get(0).getId());
        assertEquals(2, reviewsFound.get(1).getId());
    }
}