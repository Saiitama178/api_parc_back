package com.parc.api.controller;

import com.parc.api.model.dto.CommentaireDto;
import com.parc.api.model.mapper.CommentaireMapper;
import com.parc.api.repository.CommentaireRepository;
import com.parc.api.model.entity.Commentaire;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/commentaire")
@Tag(name = "commentaire", description = "Opérations sur les commentaires")
public class CommentaireController {

    private final CommentaireRepository commentaireRepository;

    @GetMapping("/commentaire")
    @Operation(summary = "Affiche la liste des commentaires", description = "Retourne une liste de tous les commentaires.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des commentaires retournée"),
                    @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
            })
    public ResponseEntity<List<CommentaireDto>> getAllCommentaire() {
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        List<CommentaireDto> commentaireDtos = commentaireList.stream()
                .map(CommentaireMapper::toDto).toList();
        return ResponseEntity.ok(commentaireDtos);
    }

    @GetMapping("/commentaire/{id}")
    @Operation(
            summary = "Affiche un commentaire par ID",
            description = "Retourne un commentaire basé sur son ID.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Commentaire trouvé"),
                    @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
            })
    public ResponseEntity<CommentaireDto> getCommentaireById(@PathVariable int id) {
        Optional<Commentaire> commentaireOptional = commentaireRepository.findById(id);
        return commentaireOptional
                .map(commentaire -> ResponseEntity.ok(CommentaireMapper.toDto(commentaire)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/commentaire")
    @Operation(summary = "Crée un nouveau commentaire",
            description = "Ajoute un nouveau commentaire à la base de données.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Commentaire créé"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide")
            })
    public ResponseEntity<CommentaireDto> createCommentaire(@RequestBody CommentaireDto commentaireDto) {
        if (commentaireDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Commentaire commentaire = CommentaireMapper.toEntity(commentaireDto);
        Commentaire savedCommentaire = commentaireRepository.save(commentaire);
        CommentaireDto savedCommentaireDto = CommentaireMapper.toDto(savedCommentaire);
        return new ResponseEntity<>(savedCommentaireDto, HttpStatus.CREATED);
    }

    @PutMapping("/commentaire/{id}")
    @Operation(
            summary = "Met à jour un commentaire",
            description = "Met à jour les informations d'un commentaire existant basé sur son ID.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Commentaire mis à jour"),
                    @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
            })
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

    @DeleteMapping("/commentaire/{id}")
    @Operation(
            summary = "Supprime un commentaire",
            description = "Supprime un commentaire basé sur son ID.",
            operationId = "commentaire",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Commentaire supprimé"),
                    @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
            })
    public ResponseEntity<Void> deleteCommentaire(@PathVariable int id) {
        Optional<Commentaire> commentaireOptional = commentaireRepository.findById(id);
        if (commentaireOptional.isPresent()) {
            commentaireRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
