package com.parc.api.controller;
import com.parc.api.model.dto.PaysDto;

import com.parc.api.service.PaysService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/pays")


public class PaysController {
    private final PaysService paysService;

    @GetMapping
    public ResponseEntity<List<PaysDto>> getAllPays() {
        List<PaysDto> paysDto = paysService.getAllPays();
        return ResponseEntity.ok(paysDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaysDto> getPaysById(@PathVariable Integer id) {
        Optional<PaysDto> paysDto = paysService.getPaysById(id);
        return paysDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //240826
    @PostMapping
    public ResponseEntity<PaysDto> createPays(@RequestBody PaysDto paysDto) {
        PaysDto createdPaysDto = paysService.createPays(paysDto);
        return new ResponseEntity<>(createdPaysDto, HttpStatus.CREATED);

    }
    }


