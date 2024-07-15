package com.parc.api.model.dto;



import com.parc.api.model.entity.ClasserId;
import lombok.*;


@Getter
@Setter

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireDto {

    private int idCommentaire;
    private String contenuCommentaire;
    private int noteParc;




}
