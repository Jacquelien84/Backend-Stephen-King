package nl.oudhoff.backendstephenking.service;

import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Mapper.UserMapper;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.Role;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.repository.RoleRepository;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = passwordEncoder;
    }

    public UserOutputDto createUser(UserInputDto userInputDto) {
        User user = UserMapper.fromInputDtoToModel(userInputDto);
        user.setPassword(encoder.encode(userInputDto.getPassword()));
        userRepo.save(user);
        Set<Role> userRoles = user.getRoles();
        for (String rolename : userInputDto.getRoles()) {
            Optional<Role> or = roleRepo.findById("ROLE_" + rolename);
            if (or.isPresent()) {
                userRoles.add(or.get());
            }
        }
        user.setRoles(userRoles);
        userRepo.save(user);
        UserOutputDto userOutputDto = UserMapper.fromModelToOutputDto(user);
        return userOutputDto;
    }

    public List<UserOutputDto> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        List<UserOutputDto> allUserOutputList = new ArrayList<>();
        for (User user : allUsers) {
            allUserOutputList.add(UserMapper.fromModelToOutputDto(user));
        }
        return allUserOutputList;
    }

    public UserOutputDto getUserById(long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User met id " + id + " niet gevonden."));
        return UserMapper.fromModelToOutputDto(user);
    }
}


