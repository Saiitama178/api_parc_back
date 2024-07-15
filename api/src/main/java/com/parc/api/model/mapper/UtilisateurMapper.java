package com.parc.api.model.mapper;

import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.model.entity.Utilisateur;

public class UtilisateurMapper {
    public static UtilisateurDto toDto(Utilisateur utilisateur) {
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setId(utilisateur.getId());
        utilisateurDto.setPseudo(utilisateur.getPseudo());
        utilisateurDto.setEmail(utilisateur.getEmail());
        utilisateurDto.setMdp(utilisateur.getMdp());
        utilisateurDto.setDateCreation(utilisateur.getDateCreation());
        utilisateurDto.setRole(Boolean.valueOf(utilisateur.getRole()));
        utilisateurDto.setToken(utilisateur.getToken());
        utilisateurDto.setIsActive(utilisateur.getIsActive());
        return utilisateurDto;
    }
    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setPseudo(utilisateurDto.getPseudo());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setMdp(utilisateurDto.getMdp());
        utilisateur.setDateCreation(utilisateurDto.getDateCreation());
        utilisateur.setRole(String.valueOf(utilisateurDto.getRole()));
        utilisateur.setToken(utilisateurDto.getToken());
        utilisateur.setIsActive(utilisateurDto.getIsActive());
        return utilisateur;
    }
}
