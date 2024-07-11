package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image", nullable = false)
    private Integer id;

    @Column(name = "ref_image", nullable = false, length = 250)
    private String refImage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type_image", nullable = false)
    private TypeImage idTypeImage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parc", nullable = false)
    private Parc idParc;

}