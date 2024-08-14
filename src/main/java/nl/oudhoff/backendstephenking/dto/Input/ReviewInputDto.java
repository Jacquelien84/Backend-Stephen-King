package nl.oudhoff.backendstephenking.dto.Input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewInputDto {
    private Long id;
    private String name;
    private LocalDate reviewDate;
    private String reviewText;
}
