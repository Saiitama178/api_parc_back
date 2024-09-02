package com.parc.api;

import com.parc.api.controller.PaysController;
import com.parc.api.model.dto.PaysDto;
import com.parc.api.service.PaysService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaysControllerTest {
    @Mock
    private PaysService paysService;

    @InjectMocks
    private PaysController paysController;

    @Test
    public void testCreatePays() {
        PaysDto paysDto = new PaysDto();
        paysDto.setNomPays("France");
        when(paysService.createPay(paysDto)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(paysDto));
        ResponseEntity<PaysDto> response = paysController.createPays(paysDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("France", response.getBody().getNomPays());

        assertEquals(paysDto, response.getBody());
    }
}
