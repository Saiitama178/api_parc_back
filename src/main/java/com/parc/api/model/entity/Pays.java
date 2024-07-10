package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pays")
public class Pays {
    @Id
    //ajout
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pays", nullable = false)
    private Integer id;

    @Column(name = "nom_pays", nullable = false, length = 50)
    private String nomPays;

}