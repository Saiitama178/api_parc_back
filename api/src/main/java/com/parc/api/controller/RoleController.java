package com.parc.api.controller;

import com.parc.api.model.dto.RoleDto;
import com.parc.api.model.entity.Role;
import com.parc.api.model.mapper.RoleMapper;
import com.parc.api.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

    private RoleRepository roleRepository;

    @GetMapping("/role")
    public ResponseEntity<List<RoleDto>> getAllRole() {
        List<Role> roleList = roleRepository.findAll();
        List<RoleDto> RoleDtoList = roleList.stream()
                .map(RoleMapper::toDto).toList();
        return ResponseEntity.ok(RoleDtoList);
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            RoleDto roleDto = RoleMapper.toDto(role.get());
            return ResponseEntity.ok(roleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/role")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        Role role = RoleMapper.toEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        RoleDto savedRoleDto = RoleMapper.toDto(savedRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoleDto);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable int id, @RequestBody RoleDto roleDto) {
        Optional<Role> foundRoleOptional = roleRepository.findById(id);
        if (foundRoleOptional.isPresent()) {
            Role foundRole = foundRoleOptional.get();
            foundRole.setLibRole(roleDto.getLibRole());
            Role savedRole = roleRepository.save(foundRole);
            RoleDto updatedRoleDto = RoleMapper.toDto(savedRole);
            return ResponseEntity.ok(updatedRoleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        Optional<Role> RoleOptional = roleRepository.findById(id);
        if (RoleOptional.isPresent()) {
            roleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

