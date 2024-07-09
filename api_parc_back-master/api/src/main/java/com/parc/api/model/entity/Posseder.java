package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posseder")
public class Posseder {
    @EmbeddedId
    private PossederId id;

    @MapsId("idReseauSociaux")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reseau_sociaux", nullable = false)
    private ReseauSociaux idReseauSociaux;

    @MapsId("idParc")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parc", nullable = false)
    private Parc idParc;

    @Column(name = "url_reseau_sociaux", nullable = false, length = 250)
    private String urlReseauSociaux;

}