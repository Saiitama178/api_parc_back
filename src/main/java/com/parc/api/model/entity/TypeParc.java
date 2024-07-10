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
@Table(name = "type_parc")
public class TypeParc {
    @Id
    @Column(name = "id_type_parc", nullable = false)
    private Integer id;

    @Column(name = "libelle_type_parc", nullable = false, length = 20)
    private String libelleTypeParc;

}