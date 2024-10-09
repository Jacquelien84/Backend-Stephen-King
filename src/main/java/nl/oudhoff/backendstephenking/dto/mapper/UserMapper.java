package nl.oudhoff.backendstephenking.dto.mapper;

import nl.oudhoff.backendstephenking.dto.input.UserInputDto;
import nl.oudhoff.backendstephenking.dto.output.UserOutputDto;
import nl.oudhoff.backendstephenking.model.User;

public class UserMapper {
    public static User fromInputDtoToModel(UserInputDto userInputDto) {
        User user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setEmail(userInputDto.getEmail());
        user.setPassword(userInputDto.getPassword());
        user.setRole(userInputDto.getRole());
        return user;
    }

    public static UserOutputDto fromModelToOutputDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setUsername(user.getUsername());
        userOutputDto.setEmail(user.getEmail());
        userOutputDto.setPassword(user.getPassword());
        userOutputDto.setRole(user.getRole());
        return userOutputDto;
    }
}