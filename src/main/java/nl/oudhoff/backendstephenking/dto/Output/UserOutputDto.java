package nl.oudhoff.backendstephenking.dto.Output;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import nl.oudhoff.backendstephenking.model.Authority;

import java.util.List;
import java.util.Set;

@Data
public class UserOutputDto {
    private String username;
    private String email;
    private String password;
    private String apikey;
    private List<ReviewOutputDto> reviews;
    @JsonSerialize
    public Set<Authority> authority;
    boolean enabled = true;
}

