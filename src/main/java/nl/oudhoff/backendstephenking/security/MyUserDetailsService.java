package nl.oudhoff.backendstephenking.security;

import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public MyUserDetailsService(UserRepository UserRepo) {
        this.userRepo = UserRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        Optional<User> ou = userRepo.findByUsernameIgnoreCase(username);
        if (ou.isPresent()) {
            User user = ou.get();
            return new MyUserDetails(user);
        }
        else {
            throw new ResourceNotFoundException(username);
        }
    }
}