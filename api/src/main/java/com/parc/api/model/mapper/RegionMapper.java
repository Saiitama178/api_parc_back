package com.parc.api.model.mapper;

import com.parc.api.model.dto.RegionDto;
import com.parc.api.model.entity.Pays;
import com.parc.api.model.entity.Region;

public class RegionMapper {
    public static RegionDto toDto(Region region) {
        RegionDto regionDto = new RegionDto();
        regionDto.setIdRegion(region.getId());
        regionDto.setNomRegion(region.getNomRegion());
        return regionDto;
    }

    public static Region toEntity(RegionDto regionDto, Pays pays) {
        Region region = new Region();
        region.setId(regionDto.getIdRegion());
        region.setNomRegion(regionDto.getNomRegion());
        region.setIdPays(pays);
        return region;
    }
}