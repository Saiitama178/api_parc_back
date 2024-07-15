package com.parc.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PossederDto {
    private String urlReseauSociaux;
    private int idReseauSociaux;
    private int idParc;
}
