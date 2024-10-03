package nl.oudhoff.backendstephenking.exception;

import java.io.Serial;

public class AuthenticationFailedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationFailedException(Throwable cause) {
        super("Authentication failed. Check your username, password and authorities and try again. Details: " + cause);
    }
}
