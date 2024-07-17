package com.parc.api.model.dto;

import com.parc.api.model.entity.TypeParc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcDto {
    private Integer id;
    private String nomParc;
    private String presentation;
    private String adresse;
    private Double longitudeParc;
    private Double latitudeParc;
    private String siteInternet;
    private String numeroTelParc;
    private Boolean isRestauration = false;
    private Boolean isBoutique = false;
    private Boolean isSejour = false;
    private Boolean isTransportCommun = false;
    private String urlAffilation;
    private ParkingDto parking;
    private VilleDto ville;
    private PeriodeDto periode;
}
