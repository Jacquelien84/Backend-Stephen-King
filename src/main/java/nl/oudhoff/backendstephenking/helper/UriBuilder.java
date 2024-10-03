package nl.oudhoff.backendstephenking.helper;

import nl.oudhoff.backendstephenking.interfaces.IdentifiableUsername;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

public class UriBuilder {
    public static URI buildUriWithUsername(IdentifiableUsername uriObject) {
        return URI.create((
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + uriObject.getUsername()).toUriString())
        );
    }
}
