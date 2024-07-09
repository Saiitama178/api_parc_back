package com.parc.api.model.mapper;




import com.parc.api.model.dto.EnregisterDto;

import com.parc.api.model.entity.Enregister;

public class EnregisterMapper {

    public static EnregisterDto toDto(Enregister enregister) {
        EnregisterDto enregisterDto = new EnregisterDto();
        enregisterDto.setIdEnregister(enregister.getId());
        return enregisterDto;
    }

    public Enregister toEntity(EnregisterDto enregistrerDto) {
        Enregister enregister = new Enregister();
        enregister.setId(enregistrerDto.getIdEnregister());
        return enregister;
    }
}
