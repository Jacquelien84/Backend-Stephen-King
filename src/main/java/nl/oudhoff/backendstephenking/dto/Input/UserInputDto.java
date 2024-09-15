package nl.oudhoff.backendstephenking.dto.Input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nl.oudhoff.backendstephenking.model.Authority;
import org.springframework.stereotype.Component;


import java.util.Set;


@Data
@Component
public class UserInputDto {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6, message = "Het wachtwoord moet tussen de 6 en 25 tekens lang zijn")
    private String password;
    private String apikey;
    @JsonSerialize
    public Set<Authority> authority;
    boolean enabled = true;
}
