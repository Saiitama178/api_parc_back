package com.parc.api;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.mapper.ParcMapper;
import com.parc.api.repository.ParcRepository;
import com.parc.api.service.ParcService;
//import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static com.parc.api.model.mapper.ParcMapper.toEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ParcServiceTest {
    @Mock
    private ParcRepository parcRepository;
    @Captor
    private ArgumentCaptor<Parc> parcArgumentCaptor;

    private ParcService parcService;
    @BeforeEach
    public void setUp() {
        parcService = new ParcService(parcRepository);
    }
    @Test
    public void getAllParc() {
        Parc parc1 = new Parc();
        parc1.setId(1);
        parc1.setNomParc("Parc1");
        Parc parc2 = new Parc();
        parc2.setId(2);
        parc2.setNomParc("Parc2");

        given(parcRepository.findAll()).willReturn(List.of(parc1, parc2));

        var response = parcService.getAllParc();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ParcDto> parcDtoList = response.getBody();
        assertEquals(2, parcDtoList.size());
        assertEquals("Parc 1", parcDtoList.get(0).getNomParc());
        assertEquals("Parc 2", parcDtoList.get(1).getNomParc());

    }
    @Test
    public void getNomParc() {
        Parc parc = new Parc();
        parc.setId(1);
        parc.setNomParc("Parc 1");

        given(parcRepository.findParcByNomParc("Parc 1")).willReturn(Optional.of(parc));

        var response = parcService.getNomParc("Parc 1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ParcDto parcDto = response.getBody();
        assertEquals("Parc 1", parcDto.getNomParc());

    }
    @Test
    public void createParc() {
        ParcDto parcDto = new ParcDto();
        parcDto.setNomParc("Parc 1");

        Parc parc = toEntity(parcDto);

        given(parcRepository.save(any(Parc.class))).willReturn(parc);

        var response = parcService.createParc(parcDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ParcDto savedPardDto = response.getBody();
        assertEquals("Parc 1", savedPardDto.getNomParc());
        verify(parcRepository).save(parcArgumentCaptor.capture());
        Parc savedParc = parcArgumentCaptor.getValue();
        assertEquals("Parc 1", savedParc.getNomParc());

    }


}
