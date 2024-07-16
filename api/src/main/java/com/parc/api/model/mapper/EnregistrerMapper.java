package com.parc.api.model.mapper;




import com.parc.api.model.dto.EnregistrerDto;

import com.parc.api.model.entity.Enregistrer;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Utilisateur;

public class EnregistrerMapper {

    public static EnregistrerDto toDto(Enregistrer enregistrer) {
        EnregistrerDto enregistrerDto = new EnregistrerDto();
        enregistrerDto.setIdEnregistrer(enregistrer.getId());
        enregistrerDto.setDateEnregistrer(enregistrer.getDateEnregistrer());
        return enregistrerDto;
    }

    public static Enregistrer toEntity(EnregistrerDto enregistrerDto, Parc parc, Utilisateur utilisateur) {
        Enregistrer enregistrer = new Enregistrer();
        enregistrer.setId(enregistrerDto.getIdEnregistrer());
        enregistrer.setDateEnregistrer(enregistrerDto.getDateEnregistrer());
        enregistrer.setIdParc(parc);
        enregistrer.setIdUtilisateur(utilisateur);
        return enregistrer;
    }
}
