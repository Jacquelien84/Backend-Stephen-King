package nl.oudhoff.backendstephenking.dto.OutputDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookOutputDto {

    private Long id;
    private String title;
    private String author;
    private String originalTitle;
    private Long released;
    private String movieAdaptation;
    private String description;
}
