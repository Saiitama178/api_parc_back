package com.parc.api.repository;

import com.parc.api.model.entity.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaysRepository extends JpaRepository<Pays, Integer> {

//List<Pays>findByName(String nomPays);
}

