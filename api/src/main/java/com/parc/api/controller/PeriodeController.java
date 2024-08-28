package com.parc.api.controller;

import com.parc.api.model.dto.PeriodeDto;
import com.parc.api.model.entity.Periode;
import com.parc.api.model.mapper.PeriodeMapper;
import com.parc.api.repository.PeriodeRepository;
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
@RequestMapping("/periode")
@Tag(name = "periode", description = "Opérations sur les périodes")
public class PeriodeController {

    private final PeriodeRepository periodeRepository;

    @GetMapping("/periode")
    @Operation(
            summary = "Affiche la liste des périodes",
            description = "Retourne une liste de toutes les périodes.",
            operationId = "periode",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Liste des périodes retournée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PeriodeDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            }
    )
    public ResponseEntity<List<PeriodeDto>> getAllPeriode() {
        List<Periode> periodeList = periodeRepository.findAll();
        List<PeriodeDto> periodeDtoList = periodeList.stream()
                .map(PeriodeMapper::toDto).toList();
        return ResponseEntity.ok(periodeDtoList);
    }

    @GetMapping("/periode/{id}")
    @Operation(
            summary = "Affiche une période par ID",
            description = "Retourne une période basée sur son ID.",
            operationId = "periode",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Période trouvée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PeriodeDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Période non trouvée")
            }
    )
    public ResponseEntity<PeriodeDto> getPeriodeById(
            @Parameter(description = "ID de la période à récupérer") @PathVariable int id) {
        Optional<Periode> periode = periodeRepository.findById(id);
        if (periode.isPresent()) {
            PeriodeDto periodeDto = PeriodeMapper.toDto(periode.get());
            return ResponseEntity.ok(periodeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/periode")
    @Operation(
            summary = "Crée une nouvelle période",
            description = "Ajoute une nouvelle période à la base de données.",
            operationId = "periode",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Période créée",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PeriodeDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            }
    )
    public ResponseEntity<PeriodeDto> createPeriodeByParc(
            @Parameter(description = "Détails de la période à créer") @RequestBody PeriodeDto periodeDto) {
        if (periodeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Periode periode = PeriodeMapper.toEntity(periodeDto);
        Periode savedPeriode = periodeRepository.save(periode);
        PeriodeDto savedPeriodeDto = PeriodeMapper.toDto(savedPeriode);
        return new ResponseEntity<>(savedPeriodeDto, HttpStatus.CREATED);
    }

    @PutMapping("/periode/{id}")
    @Operation(
            summary = "Met à jour une période",
            description = "Met à jour les informations d'une période existante basée sur son ID.",
            operationId = "periode",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Période mise à jour",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PeriodeDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Période non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")
            }
    )
    public ResponseEntity<PeriodeDto> updatePeriode(
            @Parameter(description = "ID de la période à mettre à jour") @PathVariable int id,
            @Parameter(description = "Nouvelles informations de la période") @RequestBody PeriodeDto periodeDto) {
        Optional<Periode> foundPeriodeOptional = periodeRepository.findById(id);
        if (foundPeriodeOptional.isPresent()) {
            Periode foundPeriode = foundPeriodeOptional.get();
            foundPeriode.setDateOuverture(periodeDto.getDateOuverturePeriode());
            foundPeriode.setDateFermuture(periodeDto.getDateFermeturePeriode());
            foundPeriode.setHeureOuverture(periodeDto.getHeureOuverturePeriode());
            foundPeriode.setHeureFermeture(periodeDto.getHeureFermeturePeriode());
            foundPeriode.setPrixAdulte(periodeDto.getPrixAdultePeriode());
            foundPeriode.setPrixEnfant(periodeDto.getPrixEnfantPeriode());
            Periode savedPeriode = periodeRepository.save(foundPeriode);
            PeriodeDto updatedPeriodeDto = PeriodeMapper.toDto(savedPeriode);
            return ResponseEntity.ok(updatedPeriodeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/periode/{id}")
    @Operation(
            summary = "Supprime une période",
            description = "Supprime une période basée sur son ID.",
            operationId = "periode",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Période supprimée"),
                    @ApiResponse(responseCode = "404", description = "Période non trouvée")
            }
    )
    public ResponseEntity<Void> deletePeriode(
            @Parameter(description = "ID de la période à supprimer") @PathVariable int id) {
        Optional<Periode> periodeOptional = periodeRepository.findById(id);
        if (periodeOptional.isPresent()) {
            periodeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
