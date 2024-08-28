package com.parc.api.controller;

import com.parc.api.model.dto.VilleDto;
import com.parc.api.model.entity.Ville;
import com.parc.api.model.mapper.VilleMapper;
import com.parc.api.repository.VilleRepository;
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
@RequestMapping("/ville")
@Tag(name = "ville", description = "Opérations sur les villes")
public class VilleController {

    private final VilleRepository villeRepository;

    @GetMapping("/ville")
    @Operation(
            summary = "Affiche la liste des villes",
            description = "Retourne une liste de toutes les villes.",
            operationId = "ville",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des villes retournée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            })
    public ResponseEntity<List<VilleDto>> getAllVilles() {
        List<Ville> villes = villeRepository.findAll();
        List<VilleDto> villeDto = villes.stream()
                .map(VilleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(villeDto);
    }

    @GetMapping("/ville/{id}")
    @Operation(
            summary = "Affiche une ville par ID",
            description = "Retourne une ville basée sur son ID.",
            operationId = "ville",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ville trouvée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
                    @ApiResponse(responseCode = "404", description = "Ville non trouvée")
            })
    public ResponseEntity<VilleDto> getVilleById(@PathVariable Integer id) {
        Optional<Ville> ville = villeRepository.findById(id);
        return ville
                .map(villeEntity -> ResponseEntity.ok(VilleMapper.toDto(villeEntity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/ville/{id}")
    @Operation(
            summary = "Supprime une ville",
            description = "Supprime une ville basée sur son ID.",
            operationId = "ville",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ville supprimée"),
                    @ApiResponse(responseCode = "404", description = "Ville non trouvée")
            })
    public ResponseEntity<Void> deleteVille(@PathVariable Integer id) {
        Optional<Ville> villeOptional = villeRepository.findById(id);
        if (villeOptional.isPresent()) {
            villeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/ville/{id}")
    @Operation(
            summary = "Met à jour une ville",
            description = "Met à jour les informations d'une ville existante basée sur son ID.",
            operationId = "ville",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ville mise à jour", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
                    @ApiResponse(responseCode = "404", description = "Ville non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            })
    public ResponseEntity<VilleDto> updateVille(@PathVariable Integer id, @RequestBody VilleDto villeDto) {
        Optional<Ville> foundVilleOptional = villeRepository.findById(id);
        if (foundVilleOptional.isPresent()) {
            Ville foundVille = foundVilleOptional.get();
            foundVille.setNomVille(villeDto.getNomVille());
            foundVille.setCodePostal(villeDto.getCodePostal());
            Ville savedVille = villeRepository.save(foundVille);
            VilleDto updatedVilleDto = VilleMapper.toDto(savedVille);
            return ResponseEntity.ok(updatedVilleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ville")
    @Operation(
            summary = "Crée une nouvelle ville",
            description = "Ajoute une nouvelle ville à la base de données.",
            operationId = "ville",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ville créée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            })
    public ResponseEntity<VilleDto> createVille(@RequestBody VilleDto villeDto) {
        if (villeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ville ville = VilleMapper.toEntity(villeDto);
        Ville savedVille = villeRepository.save(ville);
        VilleDto savedVilleDto = VilleMapper.toDto(savedVille);
        return new ResponseEntity<>(savedVilleDto, HttpStatus.CREATED);
    }
}




