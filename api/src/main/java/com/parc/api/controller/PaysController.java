package com.parc.api.controller;
import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.mapper.PaysMapper;
import com.parc.api.model.entity.Pays;
import com.parc.api.repository.PaysRepository;
import com.parc.api.service.PaysService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/pays")

public class PaysController {

    private final PaysService paysService;

    @GetMapping
    public ResponseEntity<List<PaysDto>> getAllPays() {
        List<PaysDto> paysDto = paysService.getAllPays();
        return ResponseEntity.ok(paysDto);
    }}





