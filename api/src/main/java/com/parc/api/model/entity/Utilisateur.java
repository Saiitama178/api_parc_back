package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur", nullable = false)
    private Integer id;

    @Column(name = "pseudo", nullable = false, length = 50)
    private String pseudo;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "mdp", nullable = false, length = 250)
    private String mdp;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "token", nullable = false, length = 250)
    private String token;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

}