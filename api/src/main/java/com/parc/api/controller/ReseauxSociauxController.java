package com.parc.api.controller;

import com.parc.api.model.dto.ReseauxSociauxDto;
import com.parc.api.model.entity.ReseauxSociaux;
import com.parc.api.model.mapper.ReseauxSociauxMapper;
import com.parc.api.repository.ReseauxSociauxRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")

public class ReseauxSociauxController {

    private final ReseauxSociauxRepository reseauxSociauxRepository;

    @GetMapping("/reseauxsociaux")
    public ResponseEntity<List<ReseauxSociauxDto>> getAllReseauxSociaux() {
        List<ReseauxSociaux> reseauxSociaux = reseauxSociauxRepository.findAll();
        List<ReseauxSociauxDto> reseauxSociauxDto = reseauxSociaux.stream()
                .map(ReseauxSociauxMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reseauxSociauxDto);
    }

    @CrossOrigin(origins = "http:localhost:3308")
    @GetMapping("/reseauxsociaux/{id}")
    public ResponseEntity<ReseauxSociauxDto> getReseauxSociauxById(@PathVariable Integer id) {
        Optional<ReseauxSociaux> reseauxSociaux = reseauxSociauxRepository.findById(id);
        if (reseauxSociaux.isPresent()) {
            ReseauxSociauxDto reseauxSociauxDto = ReseauxSociauxMapper.toDto(reseauxSociaux.get());
            return ResponseEntity.ok(reseauxSociauxDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reseauxsociaux/{id}")
    public ResponseEntity<Void> deleteReseauxSociaux(@PathVariable Integer id) {
        Optional<ReseauxSociaux> reseauxSociauxOptional = reseauxSociauxRepository.findById(id);
        if (reseauxSociauxOptional.isPresent()) {
            reseauxSociauxRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reseauxsociaux")
    public ResponseEntity<ReseauxSociauxDto> createReseauxSociaux(@RequestBody ReseauxSociauxDto newReseauxSociauxDto) {
        if (newReseauxSociauxDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ReseauxSociaux newReseauxSociaux = ReseauxSociauxMapper.toEntity(newReseauxSociauxDto);
        ReseauxSociaux savedReseauxSociaux = reseauxSociauxRepository.save(newReseauxSociaux);
        ReseauxSociauxDto savedReseauxSociauxDto = ReseauxSociauxMapper.toDto(savedReseauxSociaux);
        return new ResponseEntity<>(savedReseauxSociauxDto, HttpStatus.CREATED);
    }

    @PutMapping("/reseauxsociaux/{id}")
    public ResponseEntity<ReseauxSociauxDto> updateReseauxSociaux(@PathVariable Integer id, @RequestBody ReseauxSociauxDto reseauxSociauxDto) {
        Optional<ReseauxSociaux> reseauxSociauxOptional = reseauxSociauxRepository.findById(id);
        if (reseauxSociauxOptional.isPresent()) {
            ReseauxSociaux existingReseauxSociaux = reseauxSociauxOptional.get();
            existingReseauxSociaux.setLibReseauSociaux(reseauxSociauxDto.getLibReseauxSociaux());
            existingReseauxSociaux.setIconReseauSociaux(reseauxSociauxDto.getIconReseauxSociaux());
            ReseauxSociaux updatedReseauxSociaux = reseauxSociauxRepository.save(existingReseauxSociaux);
            ReseauxSociauxDto updatedReseauxSociauxDto = ReseauxSociauxMapper.toDto(updatedReseauxSociaux);
            return ResponseEntity.ok(updatedReseauxSociauxDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

