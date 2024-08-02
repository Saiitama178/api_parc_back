package com.parc.api.controller;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.mapper.ParcMapper;
import com.parc.api.repository.ParcRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ParcController {

    private final ParcRepository parcRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/parc")
    @Operation(summary = "Affiche la liste des parcs", description = "Retourne une liste de parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste Parc"),
                    @ApiResponse(responseCode = "404", description = "list de Parc non trouvé")
            })
    public ResponseEntity<List<ParcDto>> getAllParc() {
        List<Parc> parcList = parcRepository.findAll();
        List<ParcDto> parcDtoList = parcList.stream()
                .map(ParcMapper::toDto).toList();
        return ResponseEntity.ok(parcDtoList);
    }

    @GetMapping("/parc/{nomParc}")
    @Operation(summary = "Affiche un parc par nom", description = "Retourne un parc basé sur son nom",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parc trouvé"),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé")
            })
    public ResponseEntity<ParcDto> getNomParc(@PathVariable String nomParc) {
        return parcRepository.findParcByNomParc(nomParc)
                .map(parc -> ResponseEntity.ok(ParcMapper.toDto(parc)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/parc")
    @Operation(summary = "Crée un nouveau parc", description = "Ajoute un nouveau parc à la base de données",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Parc créé"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            })
    public ResponseEntity<ParcDto> createParc(@RequestBody ParcDto parcDto) {
        if (parcDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Parc parc = ParcMapper.toEntity(parcDto);
        Parc savedParc = parcRepository.save(parc);
        ParcDto savedParcDto = ParcMapper.toDto(savedParc);
        return new ResponseEntity<>(savedParcDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/parc/{id}")
    @Operation(summary = "Supprime un parc", description = "Supprime un parc basé sur son ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Parc supprimé"),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé")
            })
    public ResponseEntity<Void> deleteParc(@PathVariable int id) {
        if (!parcRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        parcRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/parc/{id}")
    @Operation(summary = "Met à jour un parc", description = "Met à jour les informations d'un parc existant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parc mis à jour"),
                    @ApiResponse(responseCode = "404", description = "Parc non trouvé")
            })
    public ResponseEntity<ParcDto> updateParc(@PathVariable int id, @RequestBody ParcDto parcDto) {
        if (!parcRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Parc parc = ParcMapper.toEntity(parcDto);
        parc.setId(id);
        Parc updatedParc = parcRepository.save(parc);
        ParcDto updatedParcDto = ParcMapper.toDto(updatedParc);
        return ResponseEntity.ok(updatedParcDto);
    }
}
