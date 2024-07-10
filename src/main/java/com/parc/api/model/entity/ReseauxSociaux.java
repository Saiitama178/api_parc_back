package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reseau_sociaux")
public class ReseauxSociaux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reseau_sociaux", nullable = false)
    private Integer id;

    @Column(name = "lib_reseau_sociaux", nullable = false, length = 50)
    private String libReseauxSociaux;

    @Column(name = "icon_reseau_sociaux", nullable = false)
    private byte[] iconReseauxSociaux;

}