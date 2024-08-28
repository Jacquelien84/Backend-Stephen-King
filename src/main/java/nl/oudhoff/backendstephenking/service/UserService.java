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

import java.util.*;

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
            or.ifPresent(userRoles::add);
        }
        user.setRoles(userRoles);
        userRepo.save(user);
        return UserMapper.fromModelToOutputDto(user);
    }

    public List<UserOutputDto> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        List<UserOutputDto> allUserOutputList = new ArrayList<>();
        for (User user : allUsers) {
            allUserOutputList.add(UserMapper.fromModelToOutputDto(user));
        }
        return allUserOutputList;
    }
}


