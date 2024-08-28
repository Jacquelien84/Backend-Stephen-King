package nl.oudhoff.backendstephenking.dto.Input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputDto {
    private String username;
    private String password;
    private String[] roles;
}
