package nl.oudhoff.backendstephenking.dto.output;

import lombok.Data;

@Data
public class BookOutputDto {

    public Object getId;
    private Long id;
    private String title;
    private String author;
    private String originalTitle;
    private Long released;
    private String movieAdaptation;
    private String description;
    private String bookcover;
}
