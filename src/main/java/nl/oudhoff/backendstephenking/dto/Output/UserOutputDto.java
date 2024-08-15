package nl.oudhoff.backendstephenking.dto.Output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserOutputDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<ReviewOutputDto> reviews;
    private List<String> roles;
}

