package com.parc.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parc")
public class Parc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parc", nullable = false)
    private Integer id;

    @Column(name = "nom_parc", nullable = false, length = 50)
    private String nomParc;

    @Lob
    @Column(name = "presentation", nullable = false)
    private String presentation;

    @Column(name = "adresse", nullable = false, length = 228)
    private String adresse;

    @Column(name = "longitude_parc", nullable = false)
    private Double longitudeParc;

    @Column(name = "latitude_parc", nullable = false)
    private Double latitudeParc;

    @Column(name = "site_internet", nullable = false, length = 250)
    private String siteInternet;

    @Column(name = "numero_tel_parc", nullable = false, length = 10)
    private String numeroTelParc;

    @Column(name = "is_restauration", nullable = false)
    private Boolean isRestauration = false;

    @Column(name = "is_boutique", nullable = false)
    private Boolean isBoutique = false;

    @Column(name = "is_sejour", nullable = false)
    private Boolean isSejour = false;

    @Column(name = "is_transport_commun", nullable = false)
    private Boolean isTransportCommun = false;

    @Column(name = "url_affilation", length = 250)
    private String urlAffilation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ville", nullable = false)
    private Ville idVille;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_parking", nullable = false)
    private Parking idParking;

}