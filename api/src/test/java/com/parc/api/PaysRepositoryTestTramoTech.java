package com.parc.api;

import com.parc.api.model.entity.Pays;
import com.parc.api.repository.PaysRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;
//https://www.youtube.com/watch?v=QhLlkygUhtI
@DataJpaTest
public class PaysRepositoryTestTramoTech {
    @Autowired
    PaysRepository paysRepository;
    @Autowired
    TestEntityManager em;
    @Test
    void savePays() {
        Pays pays = new Pays();
        pays.setNomPays("France");
        //paysRepository.save(pays);
        em.persist(pays);
        em.flush();
        List<Pays> savedPays = paysRepository.findAll();
        assertThat(savedPays).hasSize(1);
        assertThat(savedPays.get(0).getNomPays()).isEqualTo("France");
    }
}
