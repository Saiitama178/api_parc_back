package com.parc.api.controller;

import com.parc.api.repository.CommentaireRepository;
import com.parc.api.model.mapper.CommentaireMapper;
import com.parc.api.model.dto.CommentaireDto;
import com.parc.api.model.entity.Commentaire;
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
public class CommentaireController {

    private final CommentaireRepository commentaireRepository;

    @GetMapping("/commentaire")
    public ResponseEntity<List<CommentaireDto>> getAllCommentaire() {

        List<Commentaire> commentaireList = commentaireRepository.findAll();

        List<CommentaireDto> commentaireDtos = commentaireList.stream()
                .map(CommentaireMapper::toDto).toList();
        return ResponseEntity.ok(commentaireDtos);
    }
}
