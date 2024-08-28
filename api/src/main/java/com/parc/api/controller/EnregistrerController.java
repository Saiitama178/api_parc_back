package com.parc.api.controller;


import com.parc.api.model.dto.EnregistrerDto;
import com.parc.api.model.entity.Enregistrer;
import com.parc.api.model.mapper.EnregistrerMapper;
import com.parc.api.repository.EnregistrerRepository;
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
@RequestMapping("/enregistrer")
@Tag(name = "enregistrer", description = "Opérations liées à l'entité Enregistrer")
public class EnregistrerController {

    private final EnregistrerRepository enregistrerRepository;

    @GetMapping
    @Operation(
            summary = "Liste toutes les entrées enregistrées",
            description = "Retourne une liste de toutes les entrées de l'entité Enregistrer.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des entrées récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnregistrerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<EnregistrerDto>> getAllEnregistrer() {
        List<Enregistrer> enregistrerList = enregistrerRepository.findAll();
        List<EnregistrerDto> enregistrerDto = enregistrerList.stream()
                .map(EnregistrerMapper::toDto).toList();
        return ResponseEntity.ok(enregistrerDto);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupère une entrée enregistrée par ID",
            description = "Retourne les détails d'une entrée de l'entité Enregistrer basée sur son ID.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entrée récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnregistrerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Entrée non trouvée")
            }
    )
    public ResponseEntity<EnregistrerDto> getEnregistrerById(
            @Parameter(description = "ID de l'entrée à récupérer") @PathVariable Integer id) {
        Optional<Enregistrer> enregistrer = enregistrerRepository.findById(id);
        if (enregistrer.isPresent()) {
            EnregistrerDto enregistrerDto = EnregistrerMapper.toDto(enregistrer.get());
            return ResponseEntity.ok(enregistrerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
            summary = "Crée une nouvelle entrée",
            description = "Ajoute une nouvelle entrée à l'entité Enregistrer.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Entrée créée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnregistrerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<EnregistrerDto> createEnregistrer(
            @Parameter(description = "Détails de l'entrée à créer") @RequestBody EnregistrerDto enregistrerDto) {
        if (enregistrerDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Enregistrer enregistrer = EnregistrerMapper.toEntity(enregistrerDto);
        Enregistrer savedEnregistrer = enregistrerRepository.save(enregistrer);
        EnregistrerDto savedEnregistrerDto = EnregistrerMapper.toDto(savedEnregistrer);
        return new ResponseEntity<>(savedEnregistrerDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Met à jour une entrée",
            description = "Met à jour les informations d'une entrée basée sur son ID.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Entrée mise à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnregistrerDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Entrée non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<EnregistrerDto> updateEnregistrer(
            @Parameter(description = "ID de l'entrée à mettre à jour") @PathVariable Integer id,
            @Parameter(description = "Nouvelles informations de l'entrée") @RequestBody EnregistrerDto enregistrerDto) {
        Optional<Enregistrer> existingEnregistrer = enregistrerRepository.findById(id);
        if (existingEnregistrer.isPresent()) {
            Enregistrer enregistrer = existingEnregistrer.get();
            enregistrer = enregistrerRepository.save(enregistrer);
            return ResponseEntity.ok(EnregistrerMapper.toDto(enregistrer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprime une entrée",
            description = "Supprime une entrée de l'entité Enregistrer basée sur son ID.",
            operationId = "enregistrer",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Entrée supprimée avec succès"),
                    @ApiResponse(responseCode = "404", description = "Entrée non trouvée")
            }
    )
    public ResponseEntity<Void> deleteEnregistrer(
            @Parameter(description = "ID de l'entrée à supprimer") @PathVariable Integer id) {
        Optional<Enregistrer> enregistrerOptional = enregistrerRepository.findById(id);
        if (enregistrerOptional.isPresent()) {
            enregistrerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

