package com.parc.api.model.mapper;

import com.parc.api.model.dto.PossederDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Posseder;
import com.parc.api.model.entity.ReseauxSociaux;

public class PossederMapper {

    private static PossederDto toDto(Posseder posseder) {
        PossederDto possederDto = new PossederDto();
        possederDto.setUrlReseauSociaux(posseder.getUrlReseauSociaux());
        return possederDto;
    }

    private static Posseder toPosseder(PossederDto possederDto, Parc parc, ReseauxSociaux reseauxSociaux) {
        Posseder posseder = new Posseder();
        posseder.setUrlReseauSociaux(possederDto.getUrlReseauSociaux());
        posseder.setIdReseauSociaux(reseauxSociaux);
        posseder.setIdParc(parc);
        return posseder;
    }
}
