package com.parc.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reseau_sociaux")
public class ReseauSociaux {
    @Id
    @Column(name = "id_reseau_sociaux", nullable = false)
    private Integer id;

    @Column(name = "lib_reseau_sociaux", nullable = false, length = 50)
    private String libReseauSociaux;

    @Column(name = "icon_reseau_sociaux", nullable = false)
    private byte[] iconReseauSociaux;

}