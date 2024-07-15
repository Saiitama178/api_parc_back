package com.parc.api.controller;

import com.parc.api.model.dto.ClasserDto;
import com.parc.api.model.entity.Classer;
import com.parc.api.model.mapper.ClasserMapper;
import com.parc.api.repository.ClasserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ClasserController {
    private final ClasserRepository classerRepository;

    @GetMapping("/classer")
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
}
