package com.parc.api.controller;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.mapper.ParcMapper;
import com.parc.api.repository.ParcRepository;
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
@RequestMapping("/parc")
@Tag(name = "parc", description = "Opérations liées aux parcs")
public class ParcController {

    private final ParcRepository parcRepository;

    @GetMapping
    @Operation(
            summary = "Affiche la liste des parcs",
            description = "Retourne une liste de tous les parcs.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des parcs récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParcDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ParcDto>> getAllParc() {
        List<Parc> parcList = parcRepository.findAll();
        List<ParcDto> parcDtoList = parcList.stream()
                .map(ParcMapper::toDto).toList();
        return ResponseEntity.ok(parcDtoList);
    }

    @GetMapping("/{nomParc}")
    @Operation(
            summary = "Affiche un parc par nom",
            description = "Retourne les détails d'un parc basé sur son nom.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Parc trouvé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParcDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé")
            }
    )
    public ResponseEntity<ParcDto> getNomParc(
            @Parameter(description = "Nom du parc à rechercher") @PathVariable String nomParc) {
        return parcRepository.findParcByNomParc(nomParc)
                .map(parc -> ResponseEntity.ok(ParcMapper.toDto(parc)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Crée un nouveau parc",
            description = "Ajoute un nouveau parc à la base de données.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Parc créé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParcDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ParcDto> createParc(
            @Parameter(description = "Détails du parc à créer") @RequestBody ParcDto parcDto) {
        if (parcDto == null) {
            return ResponseEntity.badRequest().build();
        }
        Parc parc = ParcMapper.toEntity(parcDto);
        Parc savedParc = parcRepository.save(parc);
        ParcDto savedParcDto = ParcMapper.toDto(savedParc);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParcDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprime un parc",
            description = "Supprime un parc basé sur son ID.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Parc supprimé avec succès"
                    ),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé")
            }
    )
    public ResponseEntity<Void> deleteParc(
            @Parameter(description = "ID du parc à supprimer") @PathVariable int id) {
        Optional<Parc> parcOptional = parcRepository.findById(id);
        if (parcOptional.isPresent()) {
            parcRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Met à jour un parc",
            description = "Met à jour les informations d'un parc existant.",
            operationId = "parc",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Parc mis à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParcDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ParcDto> updateParc(
            @Parameter(description = "ID du parc à mettre à jour") @PathVariable int id,
            @Parameter(description = "Nouvelles informations du parc") @RequestBody ParcDto parcDto) {
        if (!parcRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Parc parc = ParcMapper.toEntity(parcDto);
        parc.setId(id); // Assurez-vous que ce champ est bien mis à jour dans votre logique métier
        Parc updatedParc = parcRepository.save(parc);
        ParcDto updatedParcDto = ParcMapper.toDto(updatedParc);
        return ResponseEntity.ok(updatedParcDto);
    }
}
