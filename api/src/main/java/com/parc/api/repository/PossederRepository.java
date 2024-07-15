package com.parc.api.repository;

import com.parc.api.model.entity.Posseder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossederRepository extends JpaRepository<Posseder, Integer> {
}
