package com.parc.api.controller;


import com.parc.api.model.dto.EnregistrerDto;

import com.parc.api.model.entity.*;
import com.parc.api.model.entity.Enregistrer;
import com.parc.api.model.mapper.EnregistrerMapper;

import com.parc.api.repository.EnregistrerRepository;
import com.parc.api.repository.ParcRepository;
import com.parc.api.repository.UtilisateurRepository;
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
public class EnregistrerController {
    private final EnregistrerRepository enregistrerRepository;
    private final ParcRepository parcRepository;
    private final UtilisateurRepository utilisateurRepository;

    @GetMapping("/enregistrer")
    public ResponseEntity<List<EnregistrerDto>> getAllEnregistrer(){

        List<Enregistrer> enregistrerList = enregistrerRepository.findAll();

        List<EnregistrerDto> enregistrerDto = enregistrerList.stream()
                .map(EnregistrerMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(enregistrerDto);

    }

    @GetMapping("/enregistrer/{id}")
    public ResponseEntity<EnregistrerDto> getEnregistrerById(@PathVariable Integer id) {
        Optional<Enregistrer> enregistrer = enregistrerRepository.findById(id);
        if (enregistrer.isPresent()) {
            EnregistrerDto enregistrerDto = EnregistrerMapper.toDto(enregistrer.get());
            return ResponseEntity.ok(enregistrerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/enregistrer/{idParc}/{idUtilisateur}")
    public ResponseEntity<EnregistrerDto> createEnregistrer(@RequestBody EnregistrerDto enregistrerDto,@PathVariable("idParc") int idParc,@PathVariable("idUtilisateur") int idUtilisateur) throws Exception {
        Parc parc = parcRepository.findById(idParc)
                .orElseThrow(()-> new Exception("erreur"));
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(()-> new Exception("erreur"));
        Enregistrer enregistrer = EnregistrerMapper.toEntity(enregistrerDto, parc, utilisateur);
        Enregistrer savedEnregistrer = enregistrerRepository.save(enregistrer);
        EnregistrerDto savedEnregistrerDto = EnregistrerMapper.toDto(savedEnregistrer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnregistrerDto);
    }

    @DeleteMapping("/enregistrer/{id}")
    public ResponseEntity<Void> deleteEnregistrer(@PathVariable Integer id) {
        Optional<Enregistrer> enregistrerOptional = enregistrerRepository.findById(id);
        if (enregistrerOptional.isPresent()) {
            enregistrerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/enregistrer/{id}")
    public ResponseEntity<EnregistrerDto> updateEnregistrer(@PathVariable Integer id, @RequestBody EnregistrerDto enregistrerDto) {
        Optional<Enregistrer> existingEnregistrer = enregistrerRepository.findById(id);
        if (existingEnregistrer.isPresent()) {
            Enregistrer enregistrer = existingEnregistrer.get();

            enregistrer = enregistrerRepository.save(enregistrer);
            return ResponseEntity.ok(EnregistrerMapper.toDto(enregistrer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


