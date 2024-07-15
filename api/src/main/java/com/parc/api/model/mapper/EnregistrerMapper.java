package com.parc.api.model.mapper;




import com.parc.api.model.dto.EnregistrerDto;

import com.parc.api.model.entity.Enregistrer;

public class EnregistrerMapper {

    public static EnregistrerDto toDto(Enregistrer enregistrer) {
        EnregistrerDto enregistrerDto = new EnregistrerDto();
        enregistrerDto.setIdEnregistrer(enregistrer.getId());
        enregistrerDto.setDateEnregistrer(enregistrer.getDateEnregistrer());
        return enregistrerDto;
    }

    public Enregistrer toEntity(EnregistrerDto enregistrerDto) {
        Enregistrer enregistrer = new Enregistrer();
        enregistrer.setId(enregistrerDto.getIdEnregistrer());
        enregistrer.setDateEnregistrer(enregistrerDto.getDateEnregistrer());
        return enregistrer;
    }
}
