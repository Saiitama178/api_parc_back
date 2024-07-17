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
                    @ApiResponse(responseCode = "200", description = " Liste Parc")
            })
    public ResponseEntity<List<ParcDto>> getAllParc() {
        List<Parc> parcList = parcRepository.findAll();
        List<ParcDto> parcDtoList = parcList.stream()
                .map(ParcMapper::toDto).toList();
        return ResponseEntity.ok(parcDtoList);
    }

    @GetMapping("/parc/{nomParc}")
    public ResponseEntity<ParcDto> getNomParc(@PathVariable String nomParc) {
        return parcRepository.findParcByNomParc(nomParc)
                .map(parc -> ResponseEntity.ok(ParcMapper.toDto(parc)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/parc")
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
    public ResponseEntity<Void> deleteParc(@PathVariable Integer id) {
        Optional<Parc> parcOptional = parcRepository.findById(id);
        if (parcOptional.isPresent()) {
            parcRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/parc/{id}")
    public ResponseEntity<ParcDto> updateParc(@PathVariable Integer id, @RequestBody ParcDto parcDto) {
        Optional<Parc> existingParc = parcRepository.findById(id);
        if (existingParc.isPresent()) {
            Parc parc = existingParc.get();
            parc.setNomParc(parcDto.getNomParc());
            parc.setPresentation(parcDto.getPresentation());
            parc.setAdresse(parcDto.getAdresse());
            parc.setLatitudeParc(parcDto.getLatitudeParc());
            parc.setLongitudeParc(parcDto.getLongitudeParc());
            parc.setSiteInternet(parcDto.getSiteInternet());
            parc.setNumeroTelParc(parcDto.getNumeroTelParc());
            parc.setIsRestauration(parcDto.getIsRestauration());
            parc.setIsBoutique(parcDto.getIsBoutique());
            parc.setIsSejour(parcDto.getIsSejour());
            parc.setIsTransportCommun(parcDto.getIsTransportCommun());
            parc.setUrlAffilation(parcDto.getUrlAffilation());
            parc = parcRepository.save(parc);
            return ResponseEntity.ok(ParcMapper.toDto(parc));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

