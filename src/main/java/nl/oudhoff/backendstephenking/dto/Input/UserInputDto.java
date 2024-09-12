package nl.oudhoff.backendstephenking.dto.Input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import nl.oudhoff.backendstephenking.model.User;

import java.util.List;


@Getter
@Setter
public class UserInputDto {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6, message = "Het wachtwoord moet tussen de 6 en 25 tekens lang zijn")
    private String password;
    private String apikey;
    private String[] roles;
}
