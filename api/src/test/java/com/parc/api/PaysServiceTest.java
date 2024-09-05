package com.parc.api;

import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.entity.Pays;
import com.parc.api.model.mapper.PaysMapper;
import com.parc.api.repository.PaysRepository;
import com.parc.api.service.PaysService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaysServiceTest {
    @Mock
    private PaysRepository paysRepository;

    @InjectMocks
    private PaysService paysService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllPays(){
        Pays pays1 = new Pays();
        pays1.setId(1);
        pays1.setNomPays("France");

        Pays pays2 = new Pays();
        pays2.setId(2);
        pays2.setNomPays("Germany");

        when(paysRepository.findAll()).thenReturn(Arrays.asList(pays1, pays2));
        ResponseEntity<List<PaysDto>> response = paysService.getAllPays();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("France", response.getBody().get(0).getNomPays());
        assertEquals("Germany", response.getBody().get(1).getNomPays());
    }
    @Test
    void testGetPaysById_Exists() {
        Pays pays = new Pays();
        pays.setId(1);
        pays.setNomPays("France");

        when(paysRepository.findById(1)).thenReturn(java.util.Optional.of(pays));
        ResponseEntity<PaysDto> response = paysService.getPaysById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("France", response.getBody().getNomPays());
    }
    @Test
    void testGetPaysById_NotFound() {
        when(paysRepository.findById(1)).thenReturn(java.util.Optional.empty());
        ResponseEntity<PaysDto> response = paysService.getPaysById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void testCreatePay() {
        PaysDto paysDto = new PaysDto(0, "France");
        Pays savedPays = new Pays();
        savedPays.setId(1);
        savedPays.setNomPays("France");

        when(paysRepository.save(any(Pays.class))).thenReturn(savedPays);

        ResponseEntity<PaysDto> response = paysService.createPay(paysDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getIdPays());
        assertEquals("France", response.getBody().getNomPays());
    }
    @Test
    void testDeletePay_Exists() {
        when(paysRepository.findById(1)).thenReturn(java.util.Optional.of(new Pays()));

        ResponseEntity<Void> response = paysService.deletePay(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(paysRepository).deleteById(1);
    }
    void testDeletePay_NotFound() {
        when(paysRepository.findById(1)).thenReturn(java.util.Optional.empty());

        ResponseEntity<Void> response = paysService.deletePay(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(paysRepository, never()).deleteById(anyInt());
    }
    @Test
    void testUpdatePays_Exists() {
        PaysDto paysDto = new PaysDto(1, "Updated France");
        Pays existingPays = new Pays();
        existingPays.setId(1);
        existingPays.setNomPays("France");

        when(paysRepository.findById(1)).thenReturn(java.util.Optional.of(existingPays));
        when(paysRepository.save(any(Pays.class))).thenReturn(existingPays);

        ResponseEntity<PaysDto> response = paysService.updatePays(1, paysDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated France", response.getBody().getNomPays());
    }
    @Test
    void testUpdatePays_NotFound() {
        PaysDto paysDto = new PaysDto(1, "Updated France");

        when(paysRepository.findById(1)).thenReturn(java.util.Optional.empty());
        ResponseEntity<PaysDto> response = paysService.updatePays(1, paysDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
