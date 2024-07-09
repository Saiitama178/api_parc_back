package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "commentaire")
public class Commentaire {
    @Id
    @Column(name = "id_commentaire", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "text_commentaire", nullable = false)
    private String textCommentaire;

    @Column(name = "note", nullable = false)
    private Integer note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur idUtilisateur;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parc", nullable = false)
    private Parc idParc;

}