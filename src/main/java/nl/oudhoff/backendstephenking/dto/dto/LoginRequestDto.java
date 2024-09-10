package nl.oudhoff.backendstephenking.dto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto() {
        this.username = username;
        this.password = password;
    }
}
