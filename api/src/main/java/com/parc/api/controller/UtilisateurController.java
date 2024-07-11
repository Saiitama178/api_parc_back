package com.parc.api.controller;

import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.model.entity.Utilisateur;
import com.parc.api.model.mapper.UtilisateurMapper;
import com.parc.api.repository.UtilisateurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UtilisateurController {

    private final UtilisateurRepository utilisateurRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user")
    @Operation(summary = "Affiche la liste des utilisateurs", description = "Retourne une liste d'utilisateur",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Liste utilisateur")
            })
    public ResponseEntity<List<UtilisateurDto>> getAllUtilisateur() {
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        List<UtilisateurDto> utilisateurDtos = utilisateurList.stream()
                .map(UtilisateurMapper::toDto).toList();
        return ResponseEntity.ok(utilisateurDtos);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable Integer id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            UtilisateurDto utilisateurDto = UtilisateurMapper.toDto(utilisateur.get());
            return ResponseEntity.ok(utilisateurDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user")
    public ResponseEntity<UtilisateurDto> createUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = UtilisateurMapper.toEntity(utilisateurDto);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        UtilisateurDto savedUtilisateurDto = UtilisateurMapper.toDto(savedUtilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUtilisateurDto);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        if (utilisateurOptional.isPresent()) {
            utilisateurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable Integer id, @RequestBody UtilisateurDto utilisateurDto) {
        Optional<Utilisateur> existingUtilisateur = utilisateurRepository.findById(id);
        if (existingUtilisateur.isPresent()) {
            Utilisateur utilisateur = existingUtilisateur.get();
            utilisateur.setPseudo(utilisateurDto.getPseudo());
            utilisateur.setEmail(utilisateurDto.getEmail());
            utilisateur.setMdp(utilisateurDto.getMdp());
            utilisateur.setDateCreation(utilisateurDto.getDateCreation());
            utilisateur.setRole(utilisateurDto.getRole());
            utilisateur.setToken(utilisateurDto.getToken());
            utilisateur.setIsActive(utilisateurDto.getIsActive());
            utilisateur = utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok(UtilisateurMapper.toDto(utilisateur));
        } else {
            return ResponseEntity.notFound().build();
        }
        }
    }

