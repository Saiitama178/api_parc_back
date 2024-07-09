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
@Table(name = "type_image")
public class TypeImage {
    @Id
    @Column(name = "id_type_image", nullable = false)
    private Integer id;

    @Column(name = "lib_type_image", nullable = false, length = 50)
    private String libTypeImage;

}