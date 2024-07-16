package com.parc.api.model.mapper;

import com.parc.api.model.dto.ClasserDto;
import com.parc.api.model.entity.Classer;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.TypeParc;

public class ClasserMapper {

    public static ClasserDto toDto(Classer classer) {
        ClasserDto classerDto = new ClasserDto();
        classerDto.setIdClasser(classer.getId());
        return classerDto;
    }
    public static Classer toEntity(ClasserDto classerDto, Parc parc, TypeParc typeParc) {
        Classer classer = new Classer();
        classer.setId(classer.getId());
        classer.setIdParc(parc);
        classer.setIdTypeParc(typeParc);
        return classer;
    }
}

