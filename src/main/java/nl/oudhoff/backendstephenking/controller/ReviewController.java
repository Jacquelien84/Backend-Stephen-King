package nl.oudhoff.backendstephenking.controller;

import jakarta.validation.Valid;
import nl.oudhoff.backendstephenking.dto.Input.ReviewInputDto;
import nl.oudhoff.backendstephenking.dto.Output.ReviewOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.helper.BindingResultHelper;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import nl.oudhoff.backendstephenking.service.BookService;
import nl.oudhoff.backendstephenking.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService, BookService bookService, UserRepository userRepo) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewInputDto reviewInputDto) {
        ReviewOutputDto createReview = reviewService.createReview(reviewInputDto);
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + reviewInputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(createReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewOutputDto> updateReview(@RequestBody ReviewInputDto reviewInputDto, @PathVariable long id) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewInputDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewOutputDto> getReviewById(@PathVariable long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/name")
    public ResponseEntity<ReviewOutputDto> getReviewByName(@RequestParam String name) {
        return ResponseEntity.ok(reviewService.getReviewByName(name));
    }

    @GetMapping
    public ResponseEntity<List<ReviewOutputDto>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReviewById(@PathVariable long id) {
        return ResponseEntity.ok(reviewService.deleteReviewById(id));
    }

    @PutMapping("/{reviewId}/users/{userId}")
    public ResponseEntity<String> addReviewToUser(@PathVariable long reviewId, @PathVariable long userId) {
        reviewService.addReviewToUser(reviewId, userId);
        return ResponseEntity.ok().body("Done");
    }

    @PutMapping("/{reviewId}/books/{bookId}/users/{userId}")
    public ResponseEntity<String> addReviewToBook(@PathVariable long reviewId, @PathVariable long bookId, @PathVariable long userId) {
        reviewService.addReviewToBook(reviewId, bookId, userId);
        return ResponseEntity.ok().body("Done");
    }
}

