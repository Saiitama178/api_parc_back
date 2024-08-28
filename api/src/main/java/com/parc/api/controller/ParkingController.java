package com.parc.api.controller;

import com.parc.api.model.dto.ParkingDto;
import com.parc.api.model.entity.Parking;
import com.parc.api.model.mapper.ParkingMapper;
import com.parc.api.repository.ParkingRepository;
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
@RequestMapping("/parking")
@Tag(name = "parking", description = "Opérations liées aux parkings")
public class ParkingController {

    private final ParkingRepository parkingRepository;

    @GetMapping
    @Operation(
            summary = "Liste tous les parkings",
            description = "Retourne une liste de tous les parkings.",
            operationId = "parking",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des parkings récupérée avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<ParkingDto>> getAllParking() {
        List<Parking> parkingList = parkingRepository.findAll();
        List<ParkingDto> parkingDtoList = parkingList.stream()
                .map(ParkingMapper::toDto).toList();
        return ResponseEntity.ok(parkingDtoList);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupère un parking par ID",
            description = "Retourne les détails d'un parking basé sur son ID.",
            operationId = "parking",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Parking récupéré avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Parking non trouvé")
            }
    )
    public ResponseEntity<ParkingDto> getParkingById(
            @Parameter(description = "ID du parking à récupérer") @PathVariable int id) {
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isPresent()) {
            ParkingDto parkingDto = ParkingMapper.toDto(parking.get());
            return ResponseEntity.ok(parkingDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
            summary = "Crée un nouveau parking",
            description = "Ajoute un nouveau parking à la base de données.",
            operationId = "parking",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Parking créé avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<ParkingDto> createParking(
            @Parameter(description = "Détails du parking à créer") @RequestBody ParkingDto parkingDto) {
        Parking parking = ParkingMapper.toEntity(parkingDto);
        Parking savedParking = parkingRepository.save(parking);
        ParkingDto savedParkingDto = ParkingMapper.toDto(savedParking);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParkingDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Met à jour un parking",
            description = "Met à jour les informations d'un parking basé sur son ID.",
            operationId = "parking",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Parking mis à jour avec succès",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Parking non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<ParkingDto> updateParking(
            @Parameter(description = "ID du parking à mettre à jour") @PathVariable int id,
            @Parameter(description = "Nouvelles informations du parking") @RequestBody ParkingDto parkingDto) {
        Optional<Parking> foundParkingOptional = parkingRepository.findById(id);
        if (foundParkingOptional.isPresent()) {
            Parking foundParking = foundParkingOptional.get();
            foundParking.setLibParking(parkingDto.getLibParking());
            Parking savedParking = parkingRepository.save(foundParking);
            ParkingDto updatedParkingDto = ParkingMapper.toDto(savedParking);
            return ResponseEntity.ok(updatedParkingDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprime un parking",
            description = "Supprime un parking basé sur son ID.",
            operationId = "parking",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Parking supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Parking non trouvé")
            }
    )
    public ResponseEntity<Void> deleteParking(
            @Parameter(description = "ID du parking à supprimer") @PathVariable int id) {
        Optional<Parking> parkingOptional = parkingRepository.findById(id);
        if (parkingOptional.isPresent()) {
            parkingRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
