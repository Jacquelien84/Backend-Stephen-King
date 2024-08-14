package nl.oudhoff.backendstephenking.dto.Input;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputDto {
    private Long id;
    private String username;
    private String password;
    private String email;
}
