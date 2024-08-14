package nl.oudhoff.backendstephenking.service;

import nl.oudhoff.backendstephenking.dto.Input.ReviewInputDto;
import nl.oudhoff.backendstephenking.dto.Mapper.ReviewMapper;
import nl.oudhoff.backendstephenking.dto.Output.ReviewOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.model.Review;
import nl.oudhoff.backendstephenking.repository.BookRepository;
import nl.oudhoff.backendstephenking.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final BookRepository bookRepo;


    public ReviewService(BookRepository bookRepo, ReviewRepository reviewRepo) {
        this.reviewRepo = reviewRepo;
        this.bookRepo = bookRepo;

    }

    public ReviewOutputDto createReview(ReviewInputDto reviewInputDto) {
        Review model = ReviewMapper.fromInputDtoToModel(reviewInputDto);
        reviewRepo.save(model);
        ReviewOutputDto reviewOutputDto = ReviewMapper.fromModelToOutputDto(model);
        if (model.getBook() != null) {
            reviewOutputDto.setBookId(model.getBook().getId());
        }
        return reviewOutputDto;
    }

    public ReviewOutputDto updateReview(long id, ReviewInputDto reviewInputDto) {
        Optional<Review> review = reviewRepo.findById(id);
        if (review.isPresent()) {
            Review model = ReviewMapper.fromInputDtoToModel(reviewInputDto);
            reviewRepo.save(model);
            return ReviewMapper.fromModelToOutputDto(model);
        } else {
            throw new ResourceNotFoundException("Review met userId " + id + " niet gevonden");
        }
    }

    public ReviewOutputDto getReviewById(long id) {
        Review review = reviewRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Opmerking met id " + id + " is niet gevonden"));
        return ReviewMapper.fromModelToOutputDto(review);
    }

    public ReviewOutputDto getReviewByName(String name) {
        Review review = reviewRepo.findByNameIgnoreCase(name).orElseThrow(() -> new ResourceNotFoundException("Opmerking van " + name + " is niet gevonden"));
        return ReviewMapper.fromModelToOutputDto(review);
    }

    public List<ReviewOutputDto> getAllReviews() {
        List<Review> allReviews = reviewRepo.findAll();
        List<ReviewOutputDto> allReviewOutputList = new ArrayList<>();
        for (Review review : allReviews) {
            allReviewOutputList.add(ReviewMapper.fromModelToOutputDto(review));
        }
        return allReviewOutputList;
    }

    public String deleteReviewById(long id) {
        Optional<Review> review = reviewRepo.findById(id);
        if (review.isPresent()) {
            reviewRepo.delete(review.get());
            return "Review with id " + id + " has been removed";
        } else {
            throw new ResourceNotFoundException("Review not found");
        }
    }

    public void addReviewToBook(long reviewId, long bookId) {
        Optional<Review> r = reviewRepo.findById(reviewId);
        Optional<Book> b = bookRepo.findById(bookId);
        if (r.isPresent() && b.isPresent()) {
            Review review = r.get();
            Book book = b.get();
            review.setBook(book);
            reviewRepo.save(review);
        } else {
            throw new ResourceNotFoundException("Een van de id's bestaat niet");
        }
    }
}
