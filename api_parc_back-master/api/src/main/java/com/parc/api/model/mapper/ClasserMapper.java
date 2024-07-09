    package com.parc.api.model.mapper;

    import com.parc.api.model.dto.ClasserDto;
    import com.parc.api.model.entity.Classer;

public class ClasserMapper {

    public static ClasserDto toDto(Classer classer) {
        ClasserDto classerDto = new ClasserDto();
        classerDto.setIdClasser(classer.getId());
        return classerDto;
    }
    public Classer toEntity(ClasserDto classerDto) {
        Classer classer = new Classer();
        classer.setId(classer.getId());
        return classer;
    }
}

