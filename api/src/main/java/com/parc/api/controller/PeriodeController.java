package com.parc.api.controller;

import com.parc.api.model.dto.PeriodeDto;
import com.parc.api.model.entity.Periode;
import com.parc.api.model.mapper.PeriodeMapper;
import com.parc.api.repository.ParcRepository;
import com.parc.api.repository.PeriodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/periode/{id}")
    public ResponseEntity<PeriodeDto> getPeriodeById(@PathVariable int id) {
        Optional<Periode> periode = periodeRepository.findById(id);
        if (periode.isPresent()) {
            PeriodeDto periodeDto = PeriodeMapper.toDto(periode.get());
            return ResponseEntity.ok(periodeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/periode")
    public ResponseEntity<PeriodeDto> createPeriodeByParc(@RequestBody PeriodeDto periodeDto) {
        if (periodeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Periode periode = PeriodeMapper.toEntity(periodeDto);
        Periode savedPeriode = periodeRepository.save(periode);
        PeriodeDto savedPeriodeDto = PeriodeMapper.toDto(savedPeriode);
        return new ResponseEntity<>(savedPeriodeDto, HttpStatus.CREATED);
    }

    @PutMapping("/periode/{id}")
    public ResponseEntity<PeriodeDto> updatePeriode(@RequestBody PeriodeDto periodeDto, @PathVariable int id) {
        Optional<Periode> foundPeriodeOptional = periodeRepository.findById(id);
        if (foundPeriodeOptional.isPresent()) {
            Periode foundPeriode = foundPeriodeOptional.get();
            foundPeriode.setDateOuverture(periodeDto.getDateOuverturePeriode());
            foundPeriode.setDateFermuture(periodeDto.getDateFermeturePeriode());
            foundPeriode.setHeureOuverture(periodeDto.getHeureOuverturePeriode());
            foundPeriode.setHeureFermeture(periodeDto.getHeureFermeturePeriode());
            foundPeriode.setPrixAdulte(periodeDto.getPrixAdultePeriode());
            foundPeriode.setPrixEnfant(periodeDto.getPrixEnfantPeriode());
            Periode savedPeriode = periodeRepository.save(foundPeriode);
            PeriodeDto updatedPeriodeDto = PeriodeMapper.toDto(savedPeriode);
            return ResponseEntity.ok(updatedPeriodeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/periode/{id}")
    public ResponseEntity<Void> deletePeriode(@PathVariable int id) {
        Optional<Periode> PeriodeOptional = periodeRepository.findById(id);
        if (PeriodeOptional.isPresent()) {
            periodeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
