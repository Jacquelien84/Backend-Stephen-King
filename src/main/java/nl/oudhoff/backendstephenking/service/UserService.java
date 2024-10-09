package nl.oudhoff.backendstephenking.service;

import nl.oudhoff.backendstephenking.dto.mapper.UserMapper;
import nl.oudhoff.backendstephenking.dto.output.UserOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserOutputDto get(String username) {
        Optional<User> model = userRepo.findByUsername(username);
        if (model.isPresent()) {
            return UserMapper.fromModelToOutputDto(model.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public List<UserOutputDto> getAll() {
        List<User> models = userRepo.findAll();
        List<UserOutputDto> userOutputDto = new ArrayList<>();

        for (User u : models) {
            userOutputDto.add(UserMapper.fromModelToOutputDto(u));
        }

        return userOutputDto;
    }

    public void deleteUser(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            userRepo.deleteByUsername(username);
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }
}

