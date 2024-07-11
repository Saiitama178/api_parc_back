package com.parc.api.model.mapper;

import com.parc.api.model.dto.VilleDto;
import com.parc.api.model.entity.Ville;

public class VilleMapper {
    public static VilleDto toDto(Ville ville) {
        VilleDto villeDto = new VilleDto();
        villeDto.setIdVille(ville.getId());
        villeDto.setNomVille(ville.getNomVille());
        villeDto.setCodePostal(ville.getCodePostal());
        return villeDto;
    }

    public static Ville toEntity(VilleDto villeDto) {
        Ville ville = new Ville();
        ville.setId(villeDto.getIdVille());
        ville.setNomVille(villeDto.getNomVille());
        ville.setCodePostal(villeDto.getCodePostal());
        return ville;
    }
}
