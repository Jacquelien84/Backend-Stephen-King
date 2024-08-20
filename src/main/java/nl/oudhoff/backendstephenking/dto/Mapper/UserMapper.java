package nl.oudhoff.backendstephenking.dto.Mapper;

import nl.oudhoff.backendstephenking.dto.Input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.Output.UserOutputDto;
import nl.oudhoff.backendstephenking.model.Role;
import nl.oudhoff.backendstephenking.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User fromInputDtoToModel(UserInputDto userInputDto) {
        User user = new User();
        user.setId(userInputDto.getId());
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setEmail(userInputDto.getEmail());
        return user;
    }

    public static UserOutputDto fromModelToOutputDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setId(user.getId());
        userOutputDto.setUsername(user.getUsername());
        userOutputDto.setPassword(user.getPassword());
        userOutputDto.setEmail(user.getEmail());
        if (user.getRoles() != null) {
            List<String> roles = new ArrayList<>();
            for (Role role : user.getRoles()) {
                roles.add(role.getRolename());
            }
            userOutputDto.setRoles(roles);
        }
        return userOutputDto;
    }
}
