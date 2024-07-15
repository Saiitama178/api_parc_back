package com.parc.api.controller;

import com.parc.api.model.dto.CommentaireDto;
import com.parc.api.model.entity.*;
import com.parc.api.model.mapper.CommentaireMapper;
import com.parc.api.repository.CommentaireRepository;
import com.parc.api.model.entity.Commentaire;
import com.parc.api.repository.ParcRepository;
import com.parc.api.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentaireController {

    private final CommentaireRepository commentaireRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ParcRepository parcRepository;

    @GetMapping("/commentaire")
    public ResponseEntity<List<CommentaireDto>> getAllCommentaire() {

        List<Commentaire> commentaireList = commentaireRepository.findAll();

        List<CommentaireDto> commentaireDtos = commentaireList.stream()
                .map(CommentaireMapper::toDto).toList();
        return ResponseEntity.ok(commentaireDtos);
    }
    @GetMapping("/commentaire/{id}")
    public ResponseEntity<CommentaireDto> getCommentaireById(@PathVariable int id) {
        Optional<Commentaire> CommentaireOptional = commentaireRepository.findById(id);
        if (CommentaireOptional.isPresent()) {
            CommentaireDto CommentaireDto = CommentaireMapper.toDto(CommentaireOptional.get());
            return ResponseEntity.ok(CommentaireDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/Commentaire")
    public ResponseEntity<CommentaireDto> createCommentaire(@RequestBody CommentaireDto commentaireDto, @PathVariable("idUtilisateur") int idUtilistaeur, @PathVariable("idParc") int idParc) throws Exception{
        Utilisateur utilisateur  = utilisateurRepository.findById(idUtilistaeur)
                .orElseThrow(()-> new Exception("Erreur"));
        Parc parc = parcRepository.findById(idParc)
                .orElseThrow(()-> new Exception("Erreur"));
        Commentaire commentaire = CommentaireMapper.toEntity(commentaireDto, utilisateur, parc);
        Commentaire savedCommentaire = commentaireRepository.save(commentaire);
        CommentaireDto savedCommentaireDto = CommentaireMapper.toDto(savedCommentaire);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCommentaireDto);
    }


@PutMapping("/Commentaire/{id}")
    public ResponseEntity<CommentaireDto> updateCommentaire(@PathVariable int id, @RequestBody CommentaireDto commentaireDto) {
        Optional<Commentaire> foundCommentaireOptional = commentaireRepository.findById(id);
        if (foundCommentaireOptional.isPresent()) {
            Commentaire foundCommentaire = foundCommentaireOptional.get();
            foundCommentaire.setTextCommentaire(commentaireDto.getContenuCommentaire());
            foundCommentaire.setNote(commentaireDto.getNoteParc());
            Commentaire savedCommentaire = commentaireRepository.save(foundCommentaire);
            CommentaireDto updatedCommentaireDto = CommentaireMapper.toDto(savedCommentaire);
            return ResponseEntity.ok(updatedCommentaireDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/Commentaire/{id}")
    public ResponseEntity<CommentaireDto> deleteCommentaire(@PathVariable int id) {
        Optional<Commentaire> CommentaireOptional = commentaireRepository.findById(id);
        if (CommentaireOptional.isPresent()) {
            commentaireRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

