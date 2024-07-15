
package com.parc.api.model.mapper;

import com.parc.api.model.dto.CommentaireDto;
import com.parc.api.model.entity.Commentaire;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Utilisateur;

public class CommentaireMapper  {
    public static CommentaireDto toDto(Commentaire commentaire) {
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setIdCommentaire(commentaire.getId());
        commentaireDto.setContenuCommentaire(commentaire.getTextCommentaire());
        commentaireDto.setNoteParc(commentaire.getNote());
        return commentaireDto;
    }
    public static Commentaire toEntity(CommentaireDto commentaireDto, Utilisateur utilisateur, Parc parc) {
        Commentaire commentaire = new Commentaire();
        commentaire.setId(commentaireDto.getIdCommentaire());
        commentaire.setTextCommentaire(commentaireDto.getContenuCommentaire());
        commentaire.setNote(commentaireDto.getNoteParc());
        commentaire.setIdUtilisateur(utilisateur);
        commentaire.setIdParc(parc);
        return commentaire;
    }

}


