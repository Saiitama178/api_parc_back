package com.parc.api.controller;

import com.parc.api.model.dto.ReseauxSociauxDto;
import com.parc.api.model.entity.ReseauxSociaux;
import com.parc.api.model.mapper.ReseauxSociauxMapper;
import com.parc.api.repository.ReseauxSociauxRepository;
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
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/reseaux-sociaux")
@Tag(name = "reseaux-sociaux", description = "Opérations sur les réseaux sociaux")
public class ReseauxSociauxController {

    private final ReseauxSociauxRepository reseauxSociauxRepository;

    @GetMapping("/reseauxsociaux")
    @Operation(
            summary = "Affiche la liste des réseaux sociaux",
            description = "Retourne une liste de tous les réseaux sociaux.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des réseaux sociaux retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReseauxSociauxDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ReseauxSociauxDto>> getAllReseauxSociaux() {
        List<ReseauxSociaux> reseauxSociaux = reseauxSociauxRepository.findAll();
        List<ReseauxSociauxDto> reseauxSociauxDto = reseauxSociaux.stream()
                .map(ReseauxSociauxMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reseauxSociauxDto);
    }

    @GetMapping("/reseauxsociaux/{id}")
    @Operation(
            summary = "Affiche un réseau social par ID",
            description = "Retourne un réseau social basé sur son ID.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Réseau social trouvé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReseauxSociauxDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Réseau social non trouvé")
            }
    )
    public ResponseEntity<ReseauxSociauxDto> getReseauxSociauxById(@PathVariable Integer id) {
        Optional<ReseauxSociaux> reseauxSociaux = reseauxSociauxRepository.findById(id);
        return reseauxSociaux
                .map(reseauxSociauxEntity -> ResponseEntity.ok(ReseauxSociauxMapper.toDto(reseauxSociauxEntity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/reseauxsociaux")
    @Operation(
            summary = "Crée un nouveau réseau social",
            description = "Ajoute un nouveau réseau social à la base de données.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Réseau social créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReseauxSociauxDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ReseauxSociauxDto> createReseauxSociaux(@RequestBody ReseauxSociauxDto newReseauxSociauxDto) {
        if (newReseauxSociauxDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ReseauxSociaux newReseauxSociaux = ReseauxSociauxMapper.toEntity(newReseauxSociauxDto);
        ReseauxSociaux savedReseauxSociaux = reseauxSociauxRepository.save(newReseauxSociaux);
        ReseauxSociauxDto savedReseauxSociauxDto = ReseauxSociauxMapper.toDto(savedReseauxSociaux);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReseauxSociauxDto);
    }

    @PutMapping("/reseauxsociaux/{id}")
    @Operation(
            summary = "Met à jour un réseau social",
            description = "Met à jour les informations d'un réseau social existant basé sur son ID.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Réseau social mis à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReseauxSociauxDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Réseau social non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ReseauxSociauxDto> updateReseauxSociaux(@PathVariable Integer id, @RequestBody ReseauxSociauxDto reseauxSociauxDto) {
        Optional<ReseauxSociaux> reseauxSociauxOptional = reseauxSociauxRepository.findById(id);
        if (reseauxSociauxOptional.isPresent()) {
            ReseauxSociaux existingReseauxSociaux = reseauxSociauxOptional.get();
            existingReseauxSociaux.setLibReseauSociaux(reseauxSociauxDto.getLibReseauxSociaux());
            ReseauxSociaux updatedReseauxSociaux = reseauxSociauxRepository.save(existingReseauxSociaux);
            ReseauxSociauxDto updatedReseauxSociauxDto = ReseauxSociauxMapper.toDto(updatedReseauxSociaux);
            return ResponseEntity.ok(updatedReseauxSociauxDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reseauxsociaux/{id}")
    @Operation(
            summary = "Supprime un réseau social",
            description = "Supprime un réseau social basé sur son ID.",
            operationId = "reseaux-sociaux",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Réseau social supprimé"),
                    @ApiResponse(responseCode = "404", description = "Réseau social non trouvé")
            }
    )
    public ResponseEntity<Void> deleteReseauxSociaux(@PathVariable Integer id) {
        Optional<ReseauxSociaux> reseauxSociauxOptional = reseauxSociauxRepository.findById(id);
        if (reseauxSociauxOptional.isPresent()) {
            reseauxSociauxRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


