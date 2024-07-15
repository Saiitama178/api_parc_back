package com.parc.api.model.mapper;

import com.parc.api.model.dto.PossederDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Posseder;
import com.parc.api.model.entity.ReseauxSociaux;

public class PossederMapper {

    public static PossederDto toDto(Posseder posseder) {
        PossederDto possederDto = new PossederDto();
        possederDto.setUrlReseauSociaux(posseder.getUrlReseauSociaux());
        return possederDto;
    }

    public static Posseder toEntity(PossederDto possederDto, Parc parc, ReseauxSociaux reseauxSociaux) {
        Posseder posseder = new Posseder();
        posseder.setUrlReseauSociaux(possederDto.getUrlReseauSociaux());
        posseder.setIdReseauSociaux(reseauxSociaux);
        posseder.setIdParc(parc);
        return posseder;
    }

}
