package nl.oudhoff.backendstephenking.dto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;

    public LoginRequestDto() {
        this.username = username;
        this.password = password;
    }
}