package com.parc.api.controller;

import com.parc.api.model.dto.ImageDto;
import com.parc.api.model.entity.Image;
import com.parc.api.model.mapper.ImageMapper;
import com.parc.api.repository.ImageRepository;
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
@RequestMapping("/image")
@Tag(name = "image", description = "Opérations liées aux images")
public class ImageController {

    private final ImageRepository imageRepository;

    @GetMapping
    @Operation(
            summary = "Liste toutes les images",
            description = "Retourne une liste de toutes les images.",
            operationId = "image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des images récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ImageDto>> getAllImages() {
        List<Image> imageList = imageRepository.findAll();
        List<ImageDto> imageDtoList = imageList.stream()
                .map(ImageMapper::toDto).toList();
        return ResponseEntity.ok(imageDtoList);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupère une image par ID",
            description = "Retourne les détails d'une image basée sur son ID.",
            operationId = "image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Image non trouvée")
            }
    )
    public ResponseEntity<ImageDto> getImageById(
            @Parameter(description = "ID de l'image à récupérer") @PathVariable int id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            ImageDto imageDto = ImageMapper.toDto(imageOptional.get());
            return ResponseEntity.ok(imageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
            summary = "Crée une nouvelle image",
            description = "Ajoute une nouvelle image à la base de données.",
            operationId = "image",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Image créée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ImageDto> createImage(
            @Parameter(description = "Détails de l'image à créer") @RequestBody ImageDto imageDto) {
        if (imageDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Image image = ImageMapper.toEntity(imageDto);
        Image savedImage = imageRepository.save(image);
        ImageDto savedImageDto = ImageMapper.toDto(savedImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImageDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Met à jour une image",
            description = "Met à jour les informations d'une image basée sur son ID.",
            operationId = "image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image mise à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Image non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ImageDto> updateImage(
            @Parameter(description = "ID de l'image à mettre à jour") @PathVariable int id,
            @Parameter(description = "Nouvelles informations de l'image") @RequestBody ImageDto imageDto) {
        Optional<Image> foundImageOptional = imageRepository.findById(id);
        if (foundImageOptional.isPresent()) {
            Image foundImage = foundImageOptional.get();
            foundImage.setRefImage(imageDto.getRefImage());
            Image savedImage = imageRepository.save(foundImage);
            ImageDto updatedImageDto = ImageMapper.toDto(savedImage);
            return ResponseEntity.ok(updatedImageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprime une image",
            description = "Supprime une image basée sur son ID.",
            operationId = "image",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Image supprimée avec succès"),
                    @ApiResponse(responseCode = "404", description = "Image non trouvée")
            }
    )
    public ResponseEntity<Void> deleteImage(
            @Parameter(description = "ID de l'image à supprimer") @PathVariable int id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            imageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
