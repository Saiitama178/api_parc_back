package com.parc.api.controller;

import com.parc.api.model.dto.TypeParcDto;
import com.parc.api.model.entity.TypeParc;
import com.parc.api.model.mapper.TypeParcMapper;
import com.parc.api.repository.TypeParcRepository;
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
public class TypeParcController {

    private final TypeParcRepository typeParcRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/typeParc")
    @Operation(summary = "Affiche la liste des types de parc", description = "Retourne une liste des types de parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Liste type parc")
            })
    public ResponseEntity<List<TypeParcDto>> getTypeParc() {
        List<TypeParc> typeParcs = typeParcRepository.findAll();
        List<TypeParcDto> typeParcDtos = typeParcs.stream()
                .map(TypeParcMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(typeParcDtos);
    }

    @GetMapping("/typeParc/{id}")
    public ResponseEntity<TypeParcDto> getTypeParcById(@PathVariable Integer id) {
        Optional<TypeParc> typeParc = typeParcRepository.findById(id);
        if (typeParc.isPresent()) {
            TypeParcDto typeParcDto = TypeParcMapper.toDto(typeParc.get());
            return ResponseEntity.ok(typeParcDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/typeParc")
    public ResponseEntity<TypeParcDto> createTypeImage(@RequestBody TypeParcDto typeParcDto) {
        if (typeParcDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TypeParc typeParc = TypeParcMapper.toEntity(typeParcDto);
        TypeParc savedTypeParc = typeParcRepository.save(typeParc);
        TypeParcDto savedTypeParcDto = TypeParcMapper.toDto(savedTypeParc);
        return new ResponseEntity<>(savedTypeParcDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/typeParc/{id}")
    public ResponseEntity<Void> deleteTypeImage(@PathVariable Integer id) {
        Optional<TypeParc> typeParcOptional = typeParcRepository.findById(id);
        if (typeParcOptional.isPresent()) {
            typeParcRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/typeParc/{id}")
    public ResponseEntity<TypeParcDto> updateTypeImage(@PathVariable Integer id, @RequestBody TypeParcDto typeParcDto) {
        Optional<TypeParc> foundTypeParcOptional = typeParcRepository.findById(id);
        if (foundTypeParcOptional.isPresent()) {
            TypeParc foundTypeParc = foundTypeParcOptional.get();
            foundTypeParc.setLibelleTypeParc(typeParcDto.getLibelleTypeParc());
            TypeParc savedTypeParc = typeParcRepository.save(foundTypeParc);
            TypeParcDto updatedTypeParcDto = TypeParcMapper.toDto(savedTypeParc);
            return ResponseEntity.ok(updatedTypeParcDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
