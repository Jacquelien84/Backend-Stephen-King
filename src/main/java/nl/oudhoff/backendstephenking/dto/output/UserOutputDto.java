package nl.oudhoff.backendstephenking.dto.output;

import lombok.Data;
import nl.oudhoff.backendstephenking.model.Role;

@Data
public class UserOutputDto { String username;
    private String email;
    private String password;
    private Role role;
}

