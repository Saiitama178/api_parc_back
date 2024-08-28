package com.parc.api.controller;

import com.parc.api.model.dto.RoleDto;
import com.parc.api.model.entity.Role;
import com.parc.api.model.mapper.RoleMapper;
import com.parc.api.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Tag(name = "role", description = "Opérations sur les rôles")
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping("/role")
    @Operation(
            summary = "Affiche la liste des rôles",
            description = "Retourne une liste de tous les rôles.",
            operationId = "role",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des rôles retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<RoleDto>> getAllRole() {
        List<Role> roleList = roleRepository.findAll();
        List<RoleDto> roleDtoList = roleList.stream()
                .map(RoleMapper::toDto).toList();
        return ResponseEntity.ok(roleDtoList);
    }

    @GetMapping("/role/{id}")
    @Operation(
            summary = "Affiche un rôle par ID",
            description = "Retourne un rôle basé sur son ID.",
            operationId = "role",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rôle trouvé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Rôle non trouvé")
            }
    )
    public ResponseEntity<RoleDto> getRoleById(@PathVariable int id) {
        Optional<Role> role = roleRepository.findById(id);
        return role
                .map(roleEntity -> ResponseEntity.ok(RoleMapper.toDto(roleEntity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/role")
    @Operation(
            summary = "Crée un nouveau rôle",
            description = "Ajoute un nouveau rôle à la base de données.",
            operationId = "role",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Rôle créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        Role role = RoleMapper.toEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        RoleDto savedRoleDto = RoleMapper.toDto(savedRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoleDto);
    }

    @PutMapping("/role/{id}")
    @Operation(
            summary = "Met à jour un rôle",
            description = "Met à jour les informations d'un rôle existant basé sur son ID.",
            operationId = "role",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rôle mis à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoleDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Rôle non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
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
    @Operation(
            summary = "Supprime un rôle",
            description = "Supprime un rôle basé sur son ID.",
            operationId = "role",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Rôle supprimé"),
                    @ApiResponse(responseCode = "404", description = "Rôle non trouvé")
            }
    )
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            roleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


