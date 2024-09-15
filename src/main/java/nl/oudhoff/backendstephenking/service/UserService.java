package nl.oudhoff.backendstephenking.service;

import jakarta.transaction.Transactional;
import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Mapper.UserMapper;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.exception.BadRequestException;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.Authority;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import nl.oudhoff.backendstephenking.security.MyUserDetails;
import nl.oudhoff.backendstephenking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public UserOutputDto createUser(UserInputDto userInputDto) {
        // Map DTO naar User model
        User user = UserMapper.fromInputDtoToModel(userInputDto);

        // Encode het wachtwoord
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Opslaan van de gebruiker
        userRepo.save(user);

        // Voeg standaardrol "ROLE_USER" toe
        user.addAuthority("ROLE_USER");
        userRepo.save(user);

        // Retourneer de UserOutputDto
        return UserMapper.fromModelToOutputDto(user);
    }

    public Set<Authority> getAuthorities(String username) {
        // Controleer of de gebruiker bestaat
        if (!userRepo.existsById(username)) {
            throw new UsernameNotFoundException(username);
        }

        // Haal de gebruiker op en retourneer zijn autoriteiten
        User user = userRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getAuthorities();
    }

    public String loginUser(String username, String password) {
        // Controleer of gebruikersnaam en wachtwoord niet null zijn
        if (username == null || password == null) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        // Haal de gebruiker op, of gooi een exception als deze niet gevonden wordt
        User user = userRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Controleer of het wachtwoord overeenkomt
        if (!encoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Invalid password");
        }

        // Genereer en retourneer een JWT-token
        MyUserDetails myUserDetails = new MyUserDetails(user);
        return jwtUtil.generateToken(myUserDetails);
    }

    public List<UserOutputDto> getAllUsers() {
        // Gebruik de stream API om gebruikers te mappen naar DTO's
        return userRepo.findAll().stream()
                .map(UserMapper::fromModelToOutputDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(String username) {
        // Controleer of de gebruiker bestaat
        Optional<User> user = userRepo.findByUsernameIgnoreCase(username);

        // Als de gebruiker bestaat, verwijder deze
        if (user.isPresent()) {
            userRepo.deleteByUsernameIgnoreCase(username);
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public void addAuthority(String username, String authority) {
        // Controleer of de gebruiker bestaat
        User user = userRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // Controleer of de gebruiker de rol al heeft
        if (user.getAuthorities().stream()
                .anyMatch(existingAuthority -> existingAuthority.getAuthority().equals(authority))) {
            throw new BadRequestException("Gebruiker heeft deze rol al");
        }

        // Voeg de nieuwe autoriteit toe en sla op
        user.addAuthority(new Authority(username, authority));
        userRepo.save(user);
    }

    public void removeAuthority(String username, String authority) {
        // Controleer of de gebruiker bestaat
        User user = userRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // Zoek de autoriteit om te verwijderen
        Authority authorityToRemove = user.getAuthorities().stream()
                .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                .findAny()
                .orElseThrow(() -> new BadRequestException("Authority not found"));

        // Verwijder de autoriteit en sla de gebruiker op
        user.removeAuthority(authorityToRemove);
        userRepo.save(user);
    }
}