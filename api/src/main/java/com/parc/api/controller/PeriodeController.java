package com.parc.api.controller;

import com.parc.api.model.dto.PeriodeDto;
import com.parc.api.model.entity.Periode;
import com.parc.api.model.mapper.PeriodeMapper;
import com.parc.api.repository.PeriodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/periode")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PeriodeController {

    private final PeriodeRepository periodeRepository;

    @GetMapping
    public ResponseEntity<List<PeriodeDto>> getAllPeriode() {
        // Récupérer tous les periodes de la base de données
        List<Periode> periodeList = periodeRepository.findAll();
        // Convertir la liste des entités Periode en liste de DTOs PeriodeDto
        List<PeriodeDto> periodeDtoList = periodeList.stream()
                .map(PeriodeMapper::toDto).toList();
        // Retourner la réponse HTTP avec la liste des Periode
        return ResponseEntity.ok(periodeDtoList);
    }
}
