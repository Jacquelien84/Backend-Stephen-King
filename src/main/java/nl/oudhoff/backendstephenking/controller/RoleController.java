package nl.oudhoff.backendstephenking.controller;

import nl.oudhoff.backendstephenking.dto.Output.RoleOutputDto;
import nl.oudhoff.backendstephenking.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleOutputDto>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }
}
