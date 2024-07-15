package com.parc.api.controller;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.dto.PossederDto;
import com.parc.api.model.entity.*;
import com.parc.api.model.mapper.PossederMapper;
import com.parc.api.repository.ParcRepository;
import com.parc.api.repository.PossederRepository;
import com.parc.api.repository.ReseauxSociauxRepository;
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
public class PossederController {

    private final PossederRepository possederRepository;
    private final ParcRepository parcRepository;
    private final ReseauxSociauxRepository reseauxSociauxRepository;

    @GetMapping("/posseder")
    @Operation(summary = "Affiche la liste de la table posseder", description = "Retourne une liste de la table posseder",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Liste posseder")
            })
    public ResponseEntity<List<PossederDto>> findAll() {
        List<Posseder> posseders = possederRepository.findAll();
        List<PossederDto> possederDtos = posseders.stream()
                .map(PossederMapper::toDto).toList();
        return ResponseEntity.ok(possederDtos);
    }

    @GetMapping("/posseder/{id}")
    public ResponseEntity<PossederDto> getPossederById(@PathVariable Integer id) {
        Optional<Posseder> posseder = possederRepository.findById(id);
        if (posseder.isPresent()) {
            PossederDto possederDto = PossederMapper.toDto(posseder.get());
            return ResponseEntity.ok(possederDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/posseder/{idParc}/{idReseauxSociaux}")
    public ResponseEntity<PossederDto> createPosseder(@RequestBody PossederDto possederDto,@PathVariable("idParc") int idParc,@PathVariable("idReseauxSociaux") int idReseauxSociaux) throws Exception {
        Parc parc = parcRepository.findById(idParc)
                .orElseThrow(()-> new Exception("erreur"));
        ReseauxSociaux reseauxSociaux = reseauxSociauxRepository.findById(idReseauxSociaux)
                .orElseThrow(()-> new Exception("erreur"));
        Posseder posseder = PossederMapper.toEntity(possederDto, parc, reseauxSociaux);
        Posseder savedPosseder = possederRepository.save(posseder);
        PossederDto savedPossederDto = PossederMapper.toDto(savedPosseder);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPossederDto);
    }

    @DeleteMapping("/posseder/{id}")
    public ResponseEntity<Void> deletePosseder(@PathVariable Integer id) {
        Optional<Posseder> possederOptional = possederRepository.findById(id);
        if (possederOptional.isPresent()) {
            possederRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/posseder/{id}")
    public ResponseEntity<PossederDto> updatePosseder(@PathVariable Integer id, @RequestBody PossederDto possederDto) {
        Optional<Posseder> existingPosseder = possederRepository.findById(id);
        if (existingPosseder.isPresent()) {
            Posseder posseder = existingPosseder.get();
            posseder.setUrlReseauSociaux(possederDto.getUrlReseauSociaux());
            posseder = possederRepository.save(posseder);
            return ResponseEntity.ok(PossederMapper.toDto(posseder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
