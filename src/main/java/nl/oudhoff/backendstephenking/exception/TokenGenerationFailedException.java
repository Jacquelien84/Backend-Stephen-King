package nl.oudhoff.backendstephenking.exception;

import java.io.Serial;

public class TokenGenerationFailedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public TokenGenerationFailedException(Throwable cause) {
        super("Token generation failed. Details: " + cause);
    }
}
