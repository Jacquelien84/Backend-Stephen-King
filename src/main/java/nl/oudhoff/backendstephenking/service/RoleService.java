package nl.oudhoff.backendstephenking.service;

import nl.oudhoff.backendstephenking.dto.Input.RoleInputDto;
import nl.oudhoff.backendstephenking.dto.Mapper.RoleMapper;
import nl.oudhoff.backendstephenking.dto.Output.RoleOutputDto;
import nl.oudhoff.backendstephenking.model.Role;
import nl.oudhoff.backendstephenking.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private RoleRepository repo;

    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }
    public List<RoleOutputDto> getRoles() {
        List<RoleOutputDto> allRoleOutputList = new ArrayList<>();
        for (Role role : repo.findAll()) {
            allRoleOutputList.add(RoleMapper.fromModelToOutputDto(role));
        }
    return allRoleOutputList;
    }
}
