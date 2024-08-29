package com.parc.api.controller;

import com.parc.api.model.dto.ClasserDto;
import com.parc.api.service.ClasserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/classer")
@Tag(name = "classer", description = "Opérations liées aux classes")
public class ClasserController {

    private final ClasserService classerService;

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
        return (ResponseEntity<List<ClasserDto>>) this.classerService.getAllClassers();
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
    public Optional<ClasserDto> getClasserById(@PathVariable Integer id) {
        return this.classerService.getClasserById(id);
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
    public ClasserDto createClasser(@RequestBody ClasserDto classerDto) {
        return this.classerService.createClasser(classerDto);
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
    public boolean deleteClasser(@PathVariable Integer id) {
        return this.classerService.deleteClasser(id);
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
    public Optional<ClasserDto> updateClasser(@PathVariable Integer id) {
        return this.classerService.updateClasser(id);
    }
}