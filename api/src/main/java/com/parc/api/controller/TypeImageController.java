package com.parc.api.controller;


import com.parc.api.model.dto.TypeImageDto;
import com.parc.api.model.entity.TypeImage;
import com.parc.api.model.mapper.TypeImageMapper;
import com.parc.api.repository.TypeImageRepository;
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
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "TypeImage", description = "Opérations sur les types d'image")
public class TypeImageController {

    private final TypeImageRepository typeImageRepository;

    @GetMapping("/typeImage")
    @Operation(
            summary = "Affiche la liste des types d'image",
            description = "Retourne une liste de tous les types d'image.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des types d'image retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TypeImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<TypeImageDto>> getAllTypeImage() {
        List<TypeImage> typeImages = typeImageRepository.findAll();
        List<TypeImageDto> typeImageDtos = typeImages.stream()
                .map(TypeImageMapper::toDto).toList();
        return ResponseEntity.ok(typeImageDtos);
    }

    @GetMapping("/typeImage/{id}")
    @Operation(
            summary = "Affiche un type d'image par ID",
            description = "Retourne un type d'image basé sur son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Type d'image trouvé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TypeImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Type d'image non trouvé")
            }
    )
    public ResponseEntity<TypeImageDto> getTypeImageById(@PathVariable Integer id) {
        Optional<TypeImage> typeImage = typeImageRepository.findById(id);
        return typeImage
                .map(typeImageEntity -> ResponseEntity.ok(TypeImageMapper.toDto(typeImageEntity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/typeImage")
    @Operation(
            summary = "Crée un nouveau type d'image",
            description = "Ajoute un nouveau type d'image à la base de données.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Type d'image créé",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TypeImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<TypeImageDto> createTypeImage(@RequestBody TypeImageDto typeImageDto) {
        if (typeImageDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TypeImage typeImage = TypeImageMapper.toEntity(typeImageDto);
        TypeImage savedTypeImage = typeImageRepository.save(typeImage);
        TypeImageDto savedTypeImageDto = TypeImageMapper.toDto(savedTypeImage);
        return new ResponseEntity<>(savedTypeImageDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/typeImage/{id}")
    @Operation(
            summary = "Supprime un type d'image",
            description = "Supprime un type d'image basé sur son ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Type d'image supprimé"),
                    @ApiResponse(responseCode = "404", description = "Type d'image non trouvé")
            }
    )
    public ResponseEntity<Void> deleteTypeImage(@PathVariable Integer id) {
        Optional<TypeImage> typeImageOptional = typeImageRepository.findById(id);
        if (typeImageOptional.isPresent()) {
            typeImageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/typeImage/{id}")
    @Operation(
            summary = "Met à jour un type d'image",
            description = "Met à jour les informations d'un type d'image existant basé sur son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Type d'image mis à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TypeImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Type d'image non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<TypeImageDto> updateTypeImage(@PathVariable Integer id, @RequestBody TypeImageDto typeImageDto) {
        Optional<TypeImage> foundTypeImageOptional = typeImageRepository.findById(id);
        if (foundTypeImageOptional.isPresent()) {
            TypeImage foundTypeImage = foundTypeImageOptional.get();
            foundTypeImage.setLibTypeImage(typeImageDto.getLibTypeImage());
            TypeImage savedTypeImage = typeImageRepository.save(foundTypeImage);
            TypeImageDto updatedTypeImageDto = TypeImageMapper.toDto(savedTypeImage);
            return ResponseEntity.ok(updatedTypeImageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

