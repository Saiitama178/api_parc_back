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
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ClasserController {



    private final ClasserRepository classerRepository;
    private final ParcRepository parcRepository;
    private final ReseauxSociauxRepository TypeParcRepository;
    private final com.parc.api.repository.TypeParcRepository typeParcRepository;

    @GetMapping("/classer")
    @Operation(summary = "Affiche la liste de la table classer", description = "Retourne une liste de la table classer",
    responses = {
        @ApiResponse(responseCode = "200", description = " Liste classer")
    })
    public ResponseEntity<List<ClasserDto>> getAllClasser() {


        // Récupérer tous les parcs de la base de données
        List<Classer> classerList = classerRepository.findAll();
        // Convertir la liste des entités Parc en liste de DTOs ParcDto
        List<ClasserDto> classerDtos = classerList.stream()
                .map(ClasserMapper::toDto)
                .collect(Collectors.toList());
        // Retourner la réponse HTTP avec la liste des ParcDto
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

    @PostMapping("/classer/{idParc}/{idTypeParc}")
    public ResponseEntity<ClasserDto> createClasser(@RequestBody ClasserDto classerDto,@PathVariable("idParc") int idParc,@PathVariable("idTypeParc") int idTypeParc) throws Exception {
        Parc parc = parcRepository.findById(idParc)
                .orElseThrow(()-> new Exception("erreur"));
        TypeParc typeParc = typeParcRepository.findById(idTypeParc)
                .orElseThrow(()-> new Exception("erreur"));
        Classer classer = ClasserMapper.toEntity(classerDto, parc, typeParc);
        Classer savedClasser = classerRepository.save(classer);
        ClasserDto savedClasserDto = ClasserMapper.toDto(savedClasser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClasserDto);
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
    public ResponseEntity<ClasserDto> updateClasser(@PathVariable Integer id, @RequestBody ClasserDto classerDto) {
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

