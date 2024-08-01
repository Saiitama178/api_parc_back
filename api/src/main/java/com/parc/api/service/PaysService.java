package com.parc.api.service;

import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.entity.Pays;
import com.parc.api.model.mapper.PaysMapper;
import com.parc.api.repository.PaysRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

//
@AllArgsConstructor
@Service

public class PaysService {
    private final PaysRepository paysRepository;

    public ResponseEntity<List<PaysDto>> getAllPays() {
        List<Pays> paysList = paysRepository.findAll();
        List<PaysDto> paysDtos = paysList.stream()
                .map(PaysMapper::toDto).toList();
        return ResponseEntity.ok(paysDtos);
    }

    public ResponseEntity<PaysDto> getPaysById(@PathVariable Integer id) {
        Optional<Pays> pays = paysRepository.findById(id);
        PaysDto paysDto;
        if (pays.isPresent()) {
            paysDto = PaysMapper.toDto(pays.get());
            return ResponseEntity.ok(paysDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        public ResponseEntity<PaysDto> createPays(PaysDto paysDto){
        Optional<Pays> paysOptional = paysRepository.findById(paysDto.getIdPays());
        if (paysOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pays déjà existant");
        }
            // Mappage du DTO à l'entité
            Pays pays = PaysMapper.toEntity(paysDto);
            // Sauvegarde de l'entité utilisateur dans la base de données
            Pays savedPays = paysRepository.save(pays);
            // Mappage de l'entité sauvegardée en DTO
            PaysDto savedPaysDto = PaysMapper.toDto(savedPays);
            // Retourne une réponse avec le statut créé
            return new ResponseEntity<>(savedPaysDto, HttpStatus.CREATED);
        }
        public ResponseEntity<PaysDto> updatePays(@PathVariable Integer id, PaysDto paysDto) {
            Optional<Pays> paysOptional = paysRepository.findById(id);
            if (paysOptional.isPresent()) {
                // Mappage du DTO à l'entité
                Pays pays = PaysMapper.toEntity(paysDto);
                // Modification de l'entité utilisateur dans la base de données
                pays.setNomPays(String.valueOf(paysOptional));
                paysRepository.save(pays);
                // Mappage de l'entité sauvegardée en DTO
                PaysDto savedPaysDto = PaysMapper.toDto(pays);
                // Retourne une réponse avec le statut modifié
                return ResponseEntity.ok(savedPaysDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        public ResponseEntity<Void> deletePays(@PathVariable Integer id) {
        Optional<Pays> paysOptional = paysRepository.findById(id);
        if (paysOptional.isPresent()) {
            paysRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        }
    }



