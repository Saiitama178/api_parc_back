package com.parc.api.controller;
import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.mapper.PaysMapper;
import com.parc.api.model.entity.Pays;
import com.parc.api.repository.PaysRepository;
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

public class PaysController {
    private final PaysRepository paysRepository;
    @CrossOrigin(origins = "http//localhost:3308")
    @GetMapping("/pays")
    public ResponseEntity<List<PaysDto>> getAllPays() {
        List<Pays> pays = paysRepository.findAll();
        List<PaysDto> paysDto = pays.stream()
                .map(PaysMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paysDto);
    }
    @CrossOrigin(origins = "http:localhost:3308")
    @GetMapping("/pays/{id}")
    public ResponseEntity<PaysDto> getPaysById(@PathVariable Integer id) {
        Optional<Pays> pays = paysRepository.findById(id);
        if (pays.isPresent()) {
            PaysDto paysDto = PaysMapper.toDto(pays.get());
            return ResponseEntity.ok(paysDto);
        } else {
            return ResponseEntity.notFound().build();
        }
        //@CrossOrigin(origins = "http:localhost:3308")
        }
    @PostMapping("/pays")
    public ResponseEntity<PaysDto> createPay(@RequestBody PaysDto payDto) {
        Pays pays = PaysMapper.toEntity(payDto);
        Pays savedPays = paysRepository.save(pays);
        PaysDto savedPayDto = PaysMapper.toDto(savedPays);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayDto);
    }
    @DeleteMapping("/pays/{id}")
    //Interger Long
    public ResponseEntity<Void> deletePay(@PathVariable Integer id) {
        Optional<Pays> paysOptional = paysRepository.findById(id);
        if (paysOptional.isPresent()) {
            paysRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        }
    @CrossOrigin(origins = "http:localhost:3308")
    @PutMapping("/pays/{id}")
    public ResponseEntity<PaysDto> updatePays(@PathVariable Integer id, @RequestBody PaysDto paysDto) {
            Optional<Pays> foundPaysOptional = paysRepository.findById(id);
            if (foundPaysOptional.isPresent()) {
                Pays foundPays = foundPaysOptional.get();
                foundPays.setNomPays(paysDto.getNomPays());
                Pays savedPays = paysRepository.save(foundPays);
                PaysDto updatedPaysDto = PaysMapper.toDto(savedPays);
                return ResponseEntity.ok(updatedPaysDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }


