package nl.oudhoff.backendstephenking.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String username) {
        super("De gebruiker " + username + " is helaas niet gevonden");
    }
    public UserNotFoundException() {
        super("De gebruiker is niet gevonden!");
    }

}
