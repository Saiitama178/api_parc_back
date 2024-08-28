package com.parc.api.controller;

import com.parc.api.model.dto.TypeParcDto;
import com.parc.api.model.entity.TypeParc;
import com.parc.api.model.mapper.TypeParcMapper;
import com.parc.api.repository.TypeParcRepository;
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
@RequestMapping("/type-parc")
@Tag(name = "type-parc", description = "Opérations sur les types de parc")
public class TypeParcController {

    private final TypeParcRepository typeParcRepository;

    @GetMapping("/typeParc")
    @Operation(
            summary = "Affiche la liste des types de parc",
            description = "Retourne une liste de tous les types de parc.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des types de parc retournée", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeParcDto.class))),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            })
    public ResponseEntity<List<TypeParcDto>> getTypeParc() {
        List<TypeParc> typeParcs = typeParcRepository.findAll();
        List<TypeParcDto> typeParcDtos = typeParcs.stream()
                .map(TypeParcMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(typeParcDtos);
    }

    @GetMapping("/typeParc/{id}")
    @Operation(
            summary = "Affiche un type de parc par ID",
            description = "Retourne un type de parc basé sur son ID.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Type de parc trouvé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeParcDto.class))),
                    @ApiResponse(responseCode = "404", description = "Type de parc non trouvé")
            })
    public ResponseEntity<TypeParcDto> getTypeParcById(@PathVariable Integer id) {
        Optional<TypeParc> typeParc = typeParcRepository.findById(id);
        return typeParc
                .map(typeParcEntity -> ResponseEntity.ok(TypeParcMapper.toDto(typeParcEntity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/typeParc")
    @Operation(
            summary = "Crée un nouveau type de parc",
            description = "Ajoute un nouveau type de parc à la base de données.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Type de parc créé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeParcDto.class))),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            })
    public ResponseEntity<TypeParcDto> createTypeParc(@RequestBody TypeParcDto typeParcDto) {
        if (typeParcDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TypeParc typeParc = TypeParcMapper.toEntity(typeParcDto);
        TypeParc savedTypeParc = typeParcRepository.save(typeParc);
        TypeParcDto savedTypeParcDto = TypeParcMapper.toDto(savedTypeParc);
        return new ResponseEntity<>(savedTypeParcDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/typeParc/{id}")
    @Operation(
            summary = "Supprime un type de parc",
            description = "Supprime un type de parc basé sur son ID.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Type de parc supprimé"),
                    @ApiResponse(responseCode = "404", description = "Type de parc non trouvé")
            })
    public ResponseEntity<Void> deleteTypeParc(@PathVariable Integer id) {
        Optional<TypeParc> typeParcOptional = typeParcRepository.findById(id);
        if (typeParcOptional.isPresent()) {
            typeParcRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/typeParc/{id}")
    @Operation(
            summary = "Met à jour un type de parc",
            description = "Met à jour les informations d'un type de parc existant basé sur son ID.",
            operationId = "type-parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Type de parc mis à jour", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TypeParcDto.class))),
                    @ApiResponse(responseCode = "404", description = "Type de parc non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            })
    public ResponseEntity<TypeParcDto> updateTypeParc(@PathVariable Integer id, @RequestBody TypeParcDto typeParcDto) {
        Optional<TypeParc> foundTypeParcOptional = typeParcRepository.findById(id);
        if (foundTypeParcOptional.isPresent()) {
            TypeParc foundTypeParc = foundTypeParcOptional.get();
            foundTypeParc.setLibelleTypeParc(typeParcDto.getLibelleTypeParc());
            TypeParc savedTypeParc = typeParcRepository.save(foundTypeParc);
            TypeParcDto updatedTypeParcDto = TypeParcMapper.toDto(savedTypeParc);
            return ResponseEntity.ok(updatedTypeParcDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

