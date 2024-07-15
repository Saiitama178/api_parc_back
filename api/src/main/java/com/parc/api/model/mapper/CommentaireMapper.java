
package com.parc.api.model.mapper;

import com.parc.api.model.dto.CommentaireDto;
import com.parc.api.model.entity.Commentaire;

public class CommentaireMapper  {
    public static CommentaireDto toDto(Commentaire commentaire) {
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setIdCommentaire(commentaire.getId());
        commentaireDto.setContenuCommentaire(commentaire.getTextCommentaire());
        commentaireDto.setNoteParc(commentaire.getNote());
        return commentaireDto;
    }
    public Commentaire toEntity(CommentaireDto commentaireDto) {
        Commentaire commentaire = new Commentaire();
        commentaire.setId(commentaireDto.getIdCommentaire());
        commentaire.setTextCommentaire(commentaireDto.getContenuCommentaire());
        commentaire.setNote(commentaireDto.getNoteParc());
        return commentaire;
    }

}


