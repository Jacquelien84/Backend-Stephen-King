package nl.oudhoff.backendstephenking.dto.Mapper;

import nl.oudhoff.backendstephenking.dto.Input.ReviewInputDto;
import nl.oudhoff.backendstephenking.dto.Output.ReviewOutputDto;
import nl.oudhoff.backendstephenking.model.Review;
import nl.oudhoff.backendstephenking.repository.BookRepository;

public class ReviewMapper {

    private static BookRepository bookRepo;

    public ReviewMapper(BookRepository bookRepo) {
        ReviewMapper.bookRepo = bookRepo;
    }

    public static Review fromInputDtoToModel(ReviewInputDto inputDto) {
        Review r = new Review();
        r.setName(inputDto.getName());
        r.setReviewDate(inputDto.getReviewDate());
        r.setReviewText(inputDto.getReviewText());
        return r;
    }

    public static ReviewOutputDto fromModelToOutputDto(Review review) {
        ReviewOutputDto reviewOutputDto = new ReviewOutputDto();
        reviewOutputDto.setId(review.getId());
        reviewOutputDto.setName(review.getName());
        reviewOutputDto.setReviewDate(review.getReviewDate());
        reviewOutputDto.setReviewText(review.getReviewText());
        if (review.getBook() != null) {
            reviewOutputDto.setBookId(review.getBook().getId());
        }
        if (review.getUser() != null) {
            reviewOutputDto.setUserId(review.getUser().getId());
        }
        return reviewOutputDto;
    }
}
