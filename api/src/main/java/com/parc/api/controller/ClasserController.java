package com.parc.api.controller;

import com.parc.api.model.dto.ClasserDto;
import com.parc.api.model.entity.*;
import com.parc.api.model.mapper.ClasserMapper;
import com.parc.api.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/classer")
@Tag(name = "classer", description = "Opérations liées aux classes")
public class ClasserController {

    private final ClasserRepository classerRepository;

    @GetMapping
    @Operation(
            summary = "Affiche la liste de la table classer",
            description = "Retourne une liste de tous les classer.",
            operationId = "classer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des classer récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClasserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ClasserDto>> getAllClasser() {
        List<Classer> classerList = classerRepository.findAll();
        List<ClasserDto> classerDtos = classerList.stream()
                .map(ClasserMapper::toDto).toList();
        return ResponseEntity.ok(classerDtos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Affiche un classer par ID",
            description = "Retourne les détails d'un classer basé sur son ID.",
            operationId = "classer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Classer trouvé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClasserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Classer non trouvé")
            }
    )
    public ResponseEntity<ClasserDto> getClasserById(
            @Parameter(description = "ID du classer à récupérer") @PathVariable Integer id) {
        Optional<Classer> classer = classerRepository.findById(id);
        return classer.map(classerEntity -> ResponseEntity.ok(ClasserMapper.toDto(classerEntity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Crée un nouveau classer",
            description = "Ajoute un nouveau classer à la base de données.",
            operationId = "classer",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Classer créé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClasserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ClasserDto> createClasser(
            @Parameter(description = "Détails du classer à créer") @RequestBody ClasserDto classerDto) {
        if (classerDto == null) {
            return ResponseEntity.badRequest().build();
        }
        Classer classer = ClasserMapper.toEntity(classerDto);
        Classer savedClasser = classerRepository.save(classer);
        ClasserDto savedClasserDto = ClasserMapper.toDto(savedClasser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClasserDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprime un classer",
            description = "Supprime un classer basé sur son ID.",
            operationId = "classer",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Classer supprimé avec succès"
                    ),
                    @ApiResponse(responseCode = "404", description = "Classer non trouvé")
            }
    )
    public ResponseEntity<Void> deleteClasser(
            @Parameter(description = "ID du classer à supprimer") @PathVariable Integer id) {
        Optional<Classer> classerOptional = classerRepository.findById(id);
        if (classerOptional.isPresent()) {
            classerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Met à jour un classer",
            description = "Met à jour les informations d'un classer existant.",
            operationId = "classer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Classer mis à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClasserDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Classer non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ClasserDto> updateClasser(
            @Parameter(description = "ID du classer à mettre à jour") @PathVariable Integer id,
            @Parameter(description = "Nouvelles informations du classer") @RequestBody ClasserDto classerDto) {
        Optional<Classer> existingClasserOptional = classerRepository.findById(id);
        if (existingClasserOptional.isPresent()) {
            Classer existingClasser = existingClasserOptional.get();
            existingClasser = classerRepository.save(existingClasser);
            ClasserDto updatedClasserDto = ClasserMapper.toDto(existingClasser);
            return ResponseEntity.ok(updatedClasserDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}