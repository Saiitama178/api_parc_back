package com.parc.api.model.mapper;

import com.parc.api.model.dto.ImageDto;
import com.parc.api.model.entity.Image;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.TypeImage;

public class ImageMapper {

    public static ImageDto toDto(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setIdImage(image.getId());
        imageDto.setRefImage(image.getRefImage());
        return imageDto;
    }

    public static Image toEntity(ImageDto imageDto, TypeImage typeImage, Parc parc) {
        Image image = new Image();
        image.setId(imageDto.getIdImage());
        image.setRefImage(imageDto.getRefImage());
        image.setIdTypeImage(typeImage);
        image.setIdParc(parc);
        return image;
    }
}
