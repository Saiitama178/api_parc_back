package com.parc.api;

import com.parc.api.controller.PaysController;
import com.parc.api.model.dto.PaysDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaysControllerTest {
    @InjectMocks
    private PaysController paysController;
    @Mock
    private PaysService paysService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllPays() {
        List<PaysDto> paysDtos = Arrays.asList(
                new PaysDto(1, "France"),
                new PaysDto(2, "Allemagne"),
                new PaysDto(3, "Spain")
        );
        when(paysService.getAllPays())
                .thenReturn(new ResponseEntity<>(paysDtos, HttpStatus.OK));

        ResponseEntity<List<PaysDto>> response = paysController.getAllPays();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paysDtos, response.getBody());
        verify(paysService, times(1)).getAllPays();
        //TODO
    }
    @Test
    void testGetPaysById(){
        PaysDto paysDto = new PaysDto(1, "France");
        when(paysService.getPaysById(1))
                .thenReturn(new ResponseEntity<>(paysDto, HttpStatus.OK));

        ResponseEntity<PaysDto> response = paysController.getPaysById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paysDto, response.getBody());
        verify(paysService, times(1)).getPaysById(1);
        verify(paysService, times(1)).getPaysById(1);
    }
    @Test
    void testCreatePays(){
        PaysDto paysDto = new PaysDto(0, "New Country");
        //
        when(paysService.createPay(paysDto))
                .thenReturn(new ResponseEntity<>(paysDto, HttpStatus.CREATED));

        ResponseEntity<PaysDto> response = paysController.createPays(paysDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        //
        assertEquals(paysDto, response.getBody());
        verify(paysService, times(1)).createPay(paysDto);
    }
    @Test
    void testDeletePays(){
        //
        when(paysService.deletePay(1))
                .thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = paysController.deletePays(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(paysService, times(1)).deletePay(1);
    }
    @Test
    void testUpdatePays(){
        PaysDto paysDto = new PaysDto(1, "New Country");
        //
        when(paysService.updatePays(1, paysDto))
                .thenReturn(new ResponseEntity<>(paysDto, HttpStatus.OK));

        ResponseEntity<PaysDto> response = paysController.updatePays(1, paysDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}
