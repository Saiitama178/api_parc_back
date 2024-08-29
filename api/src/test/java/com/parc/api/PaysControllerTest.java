package com.parc.api;

import com.parc.api.controller.PaysController;
import com.parc.api.model.dto.PaysDto;
import com.parc.api.service.PaysService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaysControllerTest {

    @Mock
    private PaysService paysService;

    @InjectMocks
    private PaysController paysController;

    private PaysDto paysDto;

    @BeforeEach
    public void setUp() {
        paysDto = new PaysDto(1, "France");
    }

    @Test
    public void testGetAllPays() {
        List<PaysDto> paysDtoList = List.of(paysDto);

        when(paysService.getAllPays()).thenReturn(paysDtoList);

        ResponseEntity<List<PaysDto>> responseEntity = paysController.getAllPays();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(paysDtoList, responseEntity.getBody());
    }
    @Test
    public void testGetPaysById() {
        when(paysService.getPaysById(anyInt())).thenReturn(Optional.ofNullable(paysDto));

        ResponseEntity<PaysDto> responseEntity = paysController.getPaysById(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(paysDto, responseEntity.getBody());
    }
    @Test
    public void testCreatePays() {
        when(paysService.createPays(any(PaysDto.class))).thenReturn(paysDto);

        ResponseEntity<PaysDto> responseEntity = paysController.createPays(paysDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(paysDto, responseEntity.getBody());
    }
    @Test
    public void testDeletePays() {
        doNothing().when(paysService).deletePays(anyInt());

        ResponseEntity<Void> responseEntity = paysController.deletePays(1);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(paysService, times(1)).deletePays(1);
    }
    @Test
    public void testUpdatePays(){
        when(paysService.updatePays(anyInt(),any(PaysDto.class))).thenReturn(paysDto);

        ResponseEntity<PaysDto> responseEntity = paysController.updatePays(1, paysDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    assertEquals(paysDto, responseEntity.getBody());
    }
}