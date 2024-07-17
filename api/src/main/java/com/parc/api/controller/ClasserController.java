package com.parc.api.controller;

import com.parc.api.model.dto.ClasserDto;
import com.parc.api.model.entity.*;
import com.parc.api.model.mapper.ClasserMapper;
import com.parc.api.repository.*;
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
public class ClasserController {

    private final ClasserRepository classerRepository;

    @GetMapping("/classer")
    @Operation(summary = "Affiche la liste de la table classer", description = "Retourne une liste de la table classer",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Liste classer")
            })
    public ResponseEntity<List<ClasserDto>> getAllClasser() {
        List<Classer> classerList = classerRepository.findAll();
        List<ClasserDto> classerDtos = classerList.stream()
                .map(ClasserMapper::toDto).toList();
        return ResponseEntity.ok(classerDtos);
    }

    @GetMapping("/classer/{id}")
    public ResponseEntity<ClasserDto> getClasserById(@PathVariable Integer id) {
        Optional<Classer> classer = classerRepository.findById(id);
        if (classer.isPresent()) {
            ClasserDto classerDto = ClasserMapper.toDto(classer.get());
            return ResponseEntity.ok(classerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/classer")
    public ResponseEntity<ClasserDto> createClasser(@RequestBody ClasserDto classerDto) {
        if (classerDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Classer classer = ClasserMapper.toEntity(classerDto);
        Classer savedClasser = classerRepository.save(classer);
        ClasserDto savedClasserDto = ClasserMapper.toDto(savedClasser);
        return new ResponseEntity<>(savedClasserDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/classer/{id}")
    public ResponseEntity<Void> deleteClasser(@PathVariable Integer id) {
        Optional<Classer> classerOptional = classerRepository.findById(id);
        if (classerOptional.isPresent()) {
            classerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/classer/{id}")
    public ResponseEntity<ClasserDto> updateClasser(@PathVariable Integer id) {
        Optional<Classer> existingClasser = classerRepository.findById(id);
        if (existingClasser.isPresent()) {
            Classer classer = existingClasser.get();
            classer = classerRepository.save(classer);
            return ResponseEntity.ok(ClasserMapper.toDto(classer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}