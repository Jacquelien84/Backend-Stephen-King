package nl.oudhoff.backendstephenking.dto.Output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewOutputDto {

    private Long id;
    private String name;
    private LocalDate reviewDate;
    private String reviewText;
    private Long bookId;
    private String username;
}