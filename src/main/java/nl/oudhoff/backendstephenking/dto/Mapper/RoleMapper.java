package nl.oudhoff.backendstephenking.dto.Mapper;

import nl.oudhoff.backendstephenking.dto.Input.RoleInputDto;
import nl.oudhoff.backendstephenking.dto.Output.RoleOutputDto;
import nl.oudhoff.backendstephenking.model.Role;

public class RoleMapper {
    public static Role fromInputDtoToModel(RoleInputDto roleInputDto) {
        Role role = new Role();
        role.setRolename(roleInputDto.getRolename());
        return role;
    }

    public static RoleOutputDto fromModelToOutputDto(Role role) {
        RoleOutputDto roleOutputDto = new RoleOutputDto();
        roleOutputDto.setRolename(role.getRolename());
        return roleOutputDto;
    }
}
