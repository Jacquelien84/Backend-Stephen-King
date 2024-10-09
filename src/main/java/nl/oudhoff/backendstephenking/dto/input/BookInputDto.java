package nl.oudhoff.backendstephenking.dto.input;

import lombok.Data;

@Data
public class BookInputDto {

    private Long id;
    private String title;
    private String author;
    private String originalTitle;
    private Long released;
    private String movieAdaptation;
    private String description;
}