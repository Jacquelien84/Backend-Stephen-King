package nl.oudhoff.backendstephenking.dto.Output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserOutputDto {
    private String username;
    private String password;
    private List<ReviewOutputDto> reviews;
    private String[] roles;
}

