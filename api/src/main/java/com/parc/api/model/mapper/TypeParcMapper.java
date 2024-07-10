package com.parc.api.model.mapper;

import com.parc.api.model.dto.TypeParcDto;
import com.parc.api.model.entity.TypeParc;

public class TypeParcMapper {
    public static TypeParcDto toDto(TypeParc type) {
        TypeParcDto typeParcDto = new TypeParcDto();
        typeParcDto.setIdTypeImage(type.getId());
        typeParcDto.setLibelleTypeParc(type.getLibelleTypeParc());
        return typeParcDto;
    }
    public TypeParc fromDto(TypeParcDto typeParcDto) {
        TypeParc typeParc = new TypeParc();
        typeParc.setId(typeParcDto.getIdTypeImage());
        typeParc.setLibelleTypeParc(typeParcDto.getLibelleTypeParc());
        return typeParc;
    }
}
