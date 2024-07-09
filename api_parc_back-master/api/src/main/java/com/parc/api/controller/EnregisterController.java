package com.parc.api.controller;

import com.parc.api.model.dto.EnregisterDto;
import com.parc.api.model.entity.Enregister;
import com.parc.api.model.mapper.EnregisterMapper;
import com.parc.api.repository.EnregisterRepository;
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
public class EnregisterController {
    private final EnregisterRepository enregisterRepository;

    @GetMapping("/enregistrer")
    public ResponseEntity<List<EnregisterDto>> getAllEnregister(){

        List<Enregister> enregisterList = enregisterRepository.findAll();

        List<EnregisterDto> enregisterDto = enregisterList.stream()
                .map(EnregisterMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(enregisterDto);

    }
}
