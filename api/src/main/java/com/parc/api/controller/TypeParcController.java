package com.parc.api.controller;

import com.parc.api.model.dto.TypeParcDto;
import com.parc.api.model.entity.TypeParc;
import com.parc.api.model.mapper.TypeParcMapper;
import com.parc.api.repository.TypeParcRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
}
