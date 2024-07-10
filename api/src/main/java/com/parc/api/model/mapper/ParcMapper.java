package com.parc.api.model.mapper;


import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.entity.Parc;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class ParcMapper {


    public static ParcDto toDto(Parc parc) {
        ParcDto parcDto = new ParcDto();
        parcDto.setId(parc.getId());
        parcDto.setNomParc(parc.getNomParc());
        parcDto.setPresentation(parc.getPresentation());
        parcDto.setAdresse(parc.getAdresse());
        parcDto.setLatitudeParc(parc.getLatitudeParc());
        parcDto.setLongitudeParc(parc.getLongitudeParc());
        parcDto.setSiteInternet(parc.getSiteInternet());
        parcDto.setNumeroTelParc(parc.getNumeroTelParc());
        parcDto.setIsRestauration(parc.getIsRestauration());
        parcDto.setIsBoutique(parc.getIsBoutique());
        parcDto.setIsSejour(parc.getIsSejour());
        parcDto.setIsTransportCommun(parc.getIsTransportCommun());
        parcDto.setUrlAffilation(parc.getUrlAffilation());
        return parcDto;
    }

    public static Parc toEntity(ParcDto parcDto) {
        Parc parc = new Parc();
        parc.setId(parcDto.getId());
        parc.setNomParc(parcDto.getNomParc());
        parc.setPresentation(parcDto.getPresentation());
        parc.setAdresse(parcDto.getAdresse());
        parc.setLatitudeParc(parcDto.getLatitudeParc());
        parc.setLongitudeParc(parcDto.getLongitudeParc());
        parc.setSiteInternet(parcDto.getSiteInternet());
        parc.setNumeroTelParc(parcDto.getNumeroTelParc());
        parc.setIsRestauration(parcDto.getIsRestauration());
        parc.setIsBoutique(parcDto.getIsBoutique());
        parc.setIsSejour(parcDto.getIsSejour());
        parc.setIsTransportCommun(parcDto.getIsTransportCommun());
        parc.setUrlAffilation(parcDto.getUrlAffilation());
        return parc;
    }
}

