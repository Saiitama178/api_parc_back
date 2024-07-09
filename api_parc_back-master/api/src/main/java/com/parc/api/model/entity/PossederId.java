package com.parc.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PossederId implements java.io.Serializable {
    private static final long serialVersionUID = -1401902753006990431L;
    @Column(name = "id_reseau_sociaux", nullable = false)
    private Integer idReseauSociaux;

    @Column(name = "id_parc", nullable = false)
    private Integer idParc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PossederId entity = (PossederId) o;
        return Objects.equals(this.idReseauSociaux, entity.idReseauSociaux) &&
                Objects.equals(this.idParc, entity.idParc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReseauSociaux, idParc);
    }

}