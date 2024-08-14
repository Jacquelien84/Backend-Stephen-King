package nl.oudhoff.backendstephenking.dto.Input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInputDto {

    private Long id;
    private String title;
    private String author;
    private String originalTitle;
    private Long released;
    private String movieAdaptation;
    private String description;

}