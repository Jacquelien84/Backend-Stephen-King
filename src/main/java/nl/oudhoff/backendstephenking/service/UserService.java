package nl.oudhoff.backendstephenking.service;

import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Mapper.UserMapper;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.exception.ResourceNotFoundException;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserOutputDto createUser(UserInputDto userInputDto) {
        User model = UserMapper.fromInputDtoToModel(userInputDto);
        userRepo.save(model);
        return UserMapper.fromModelToOutputDto(model);
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


