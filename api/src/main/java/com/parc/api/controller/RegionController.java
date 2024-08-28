package com.parc.api.controller;

import com.parc.api.model.dto.RegionDto;
import com.parc.api.model.entity.Region;
import com.parc.api.model.mapper.RegionMapper;
import com.parc.api.repository.RegionRepository;
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
@RequestMapping("/region")
@Tag(name = "region", description = "Opérations sur les régions")
public class RegionController {
    private final RegionRepository regionRepository;

    @GetMapping("/region")
    @Operation(
            summary = "Affiche la liste des régions",
            description = "Retourne une liste de toutes les régions.",
            operationId = "region",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des régions retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<RegionDto>> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        List<RegionDto> regionDto = regions.stream()
                .map(RegionMapper::toDto).toList();
        return ResponseEntity.ok(regionDto);
    }

    @CrossOrigin(origins = "http://localhost:3308")
    @GetMapping("/region/{id}")
    @Operation(
            summary = "Affiche une région par ID",
            description = "Retourne une région basée sur son ID.",
            operationId = "region",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Région trouvée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Région non trouvée")
            }
    )
    public ResponseEntity<RegionDto> getRegionById(@PathVariable Integer id) {
        Optional<Region> region = regionRepository.findById(id);
        if (region.isPresent()) {
            RegionDto regionDto = RegionMapper.toDto(region.get());
            return ResponseEntity.ok(regionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/region")
    @Operation(
            summary = "Crée une nouvelle région",
            description = "Ajoute une nouvelle région à la base de données.",
            operationId = "region",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Région créée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<RegionDto> createRegionByPays(@RequestBody RegionDto regionDto) {
        if (regionDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Region region = RegionMapper.toEntity(regionDto);
        Region savedRegion = regionRepository.save(region);
        RegionDto savedRegionDto = RegionMapper.toDto(savedRegion);
        return new ResponseEntity<>(savedRegionDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/region/{id}")
    @Operation(
            summary = "Supprime une région",
            description = "Supprime une région basée sur son ID.",
            operationId = "region",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Région supprimée"),
                    @ApiResponse(responseCode = "404", description = "Région non trouvée")
            }
    )
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        Optional<Region> regionOptional = regionRepository.findById(id);
        if (regionOptional.isPresent()) {
            regionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/region/{id}")
    @Operation(
            summary = "Met à jour une région",
            description = "Met à jour les informations d'une région existante basée sur son ID.",
            operationId = "region",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Région mise à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegionDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Région non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<RegionDto> updateRegion(@PathVariable Integer id, @RequestBody RegionDto regionDto) {
        Optional<Region> foundRegionOptional = regionRepository.findById(id);
        if (foundRegionOptional.isPresent()) {
            Region foundRegion = foundRegionOptional.get();
            foundRegion.setNomRegion(regionDto.getNomRegion());
            Region savedRegion = regionRepository.save(foundRegion);
            RegionDto updatedRegionDto = RegionMapper.toDto(savedRegion);
            return ResponseEntity.ok(updatedRegionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



