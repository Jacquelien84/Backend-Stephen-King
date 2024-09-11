package nl.oudhoff.backendstephenking.service;

import jakarta.transaction.Transactional;
import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Mapper.UserMapper;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.repository.RoleRepository;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import nl.oudhoff.backendstephenking.security.MyUserDetails;
import nl.oudhoff.backendstephenking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public UserOutputDto createUser(UserInputDto userInputDto) {
        User user = UserMapper.fromInputDtoToModel(userInputDto);
        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        userRepo.save(user);
        return UserMapper.fromModelToOutputDto(user);
    }


    public String loginUser(String username, String password) {
        if (username == null || password == null) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        User user = userRepo.findByUsernameIgnoreCase(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Invalid password");
        }

        MyUserDetails myUserDetails = new MyUserDetails(user);
        return jwtUtil.generateToken(myUserDetails);
    }

    public List<UserOutputDto> getAllUsers() {
        List<User> allUsers = userRepo.findAll();
        List<UserOutputDto> allUserOutputList = new ArrayList<>();
        for (User user : allUsers) {
            allUserOutputList.add(UserMapper.fromModelToOutputDto(user));
        }
        return allUserOutputList;
    }

    public void deleteUser(String username) {
        Optional<User> user = userRepo.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            userRepo.deleteByUsernameIgnoreCase(username);
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }
}

