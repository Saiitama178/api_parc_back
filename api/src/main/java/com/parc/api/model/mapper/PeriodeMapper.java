package com.parc.api.model.mapper;

import com.parc.api.model.dto.PeriodeDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Periode;

public class PeriodeMapper {

    public static PeriodeDto toDto(Periode periode) {
        PeriodeDto periodeDto = new PeriodeDto();
        periodeDto.setIdPeriode(periode.getId());
        periodeDto.setDateOuverturePeriode(periode.getDateOuverture());
        periodeDto.setDateFermeturePeriode(periode.getDateFermuture());
        periodeDto.setHeureOuverturePeriode(periode.getHeureOuverture());
        periodeDto.setHeureFermeturePeriode(periode.getHeureFermeture());
        periodeDto.setPrixAdultePeriode(periode.getPrixAdulte());
        periodeDto.setPrixEnfantPeriode(periode.getPrixEnfant());
        return periodeDto;
    }

    public static Periode toEntity(PeriodeDto periodeDto, Parc parc) {
        Periode periode = new Periode();
        periode.setId(periodeDto.getIdPeriode());
        periode.setDateOuverture(periodeDto.getDateOuverturePeriode());
        periode.setDateFermuture(periodeDto.getDateFermeturePeriode());
        periode.setHeureOuverture(periodeDto.getHeureOuverturePeriode());
        periode.setHeureFermeture(periodeDto.getHeureFermeturePeriode());
        periode.setPrixAdulte(periodeDto.getPrixAdultePeriode());
        periode.setPrixEnfant(periodeDto.getPrixEnfantPeriode());
        periode.setIdParc(parc);
        return periode;
    }
}
