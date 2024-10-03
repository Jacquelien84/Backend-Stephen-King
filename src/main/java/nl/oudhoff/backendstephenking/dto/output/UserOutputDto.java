package nl.oudhoff.backendstephenking.dto.output;

import lombok.Data;
import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.model.Role;

import java.util.Set;

@Data
public class UserOutputDto { String username;
    private String email;
    private String password;
    private Role role;
    private Set<Book> favouriteBooks;


}

