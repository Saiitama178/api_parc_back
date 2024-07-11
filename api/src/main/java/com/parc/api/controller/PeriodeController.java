package com.parc.api.controller;

import com.parc.api.model.dto.PeriodeDto;
import com.parc.api.model.entity.Periode;
import com.parc.api.model.mapper.PeriodeMapper;
import com.parc.api.repository.PeriodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PeriodeController {

    private final PeriodeRepository periodeRepository;

    @GetMapping("/periode")
    public ResponseEntity<List<PeriodeDto>> getAllPeriode() {
        List<Periode> periodeList = periodeRepository.findAll();
        List<PeriodeDto> periodeDtoList = periodeList.stream()
                .map(PeriodeMapper::toDto).toList();
        return ResponseEntity.ok(periodeDtoList);
    }
}
