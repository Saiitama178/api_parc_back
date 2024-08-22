package com.parc.api.service;

import com.parc.api.model.dto.PaysDto;

import java.util.List;

public interface PaysService {
    List<PaysDto> getAllPays();
    PaysDto getPaysById(Integer id);
    PaysDto createPays(PaysDto paysDto);
    void deletePays(Integer id);
    PaysDto updatePays(Integer id, PaysDto paysDto);
}
