package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "type_image")
public class TypeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_image", nullable = false)
    private Integer id;

    @Column(name = "lib_type_image", nullable = false, length = 50)
    private String libTypeImage;

}