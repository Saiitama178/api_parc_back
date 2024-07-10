package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ville")
public class Ville {
    @Id
    @Column(name = "id_ville", nullable = false)
    private Integer id;

    @Column(name = "nom_ville", nullable = false, length = 50)
    private String nomVille;

    @Column(name = "code_porsatal", nullable = false, length = 5)
    private String codePorsatal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_region", nullable = false)
    private Region idRegion;

}