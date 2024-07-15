package com.parc.api.controller;


import com.parc.api.model.dto.EnregistrerDto;
import com.parc.api.model.entity.Enregistrer;
import com.parc.api.model.mapper.EnregistrerMapper;
import com.parc.api.repository.EnregistrerRepository;
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
public class EnregistrerController {
    private final EnregistrerRepository enregistrerRepository;

    @GetMapping("/enregistrer")
    public ResponseEntity<List<EnregistrerDto>> getAllEnregistrer(){

        List<Enregistrer> enregistrerList = enregistrerRepository.findAll();

        List<EnregistrerDto> enregistrerDto = enregistrerList.stream()
                .map(EnregistrerMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(enregistrerDto);

    }
}
