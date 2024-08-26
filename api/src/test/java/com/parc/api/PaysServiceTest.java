package com.parc.api;

import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.entity.Pays;
import com.parc.api.repository.PaysRepository;
import com.parc.api.service.PaysService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class PaysServiceTest {
    @Autowired
    private PaysService paysService;

    @Autowired
    private PaysRepository paysRepository;

    private Pays testPays;

    @BeforeEach
    void setUp() {
        testPays = new Pays();
        testPays.setNomPays("TestCountry");
        testPays = paysRepository.save(testPays);
    }
    @Test
    void getAllPays() {
        List<PaysDto> result = paysService.getAllPays();
        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(pays -> pays.getNomPays().equals("TestCountry")));

    }

}
