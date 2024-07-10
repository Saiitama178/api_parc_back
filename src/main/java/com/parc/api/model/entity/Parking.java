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
@Table(name = "parking")
public class Parking {
    @Id
    @Column(name = "id_parking", nullable = false)
    private Integer id;

    @Column(name = "lib_parking", nullable = false, length = 50)
    private String libParking;

}