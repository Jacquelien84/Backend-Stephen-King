package nl.oudhoff.backendstephenking.dto.output;

import lombok.Getter;
import lombok.Setter;
import nl.oudhoff.backendstephenking.model.User;

import java.util.Set;

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
    private String bookcover;
    private Set<User> favourites;
}
