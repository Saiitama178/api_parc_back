package com.parc.api.service.impl;

import com.parc.api.controller.PaysController;
import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.entity.Pays;
import com.parc.api.model.mapper.PaysMapper;
import com.parc.api.repository.PaysRepository;
import com.parc.api.service.PaysService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaysServiceImpl implements PaysService {

    private final PaysRepository paysRepository;

    @Override
    public List<PaysDto> getAllPays() {
        return paysRepository.findAll().stream()
                .map(PaysMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaysDto getPaysById(Integer id) {
        return paysRepository.findById(id)
                .map(PaysMapper::toDto)
                .orElse(null);
    }

    @Override
    public PaysDto createPays(PaysDto paysDto) {
        Pays pays = PaysMapper.toEntity(paysDto);
        Pays savedPays = paysRepository.save(pays);
        return PaysMapper.toDto(savedPays);
    }

    @Override
    public void deletePays(Integer id) {
        paysRepository.deleteById(id);
    }

    @Override
    public PaysDto updatePays(Integer id, PaysDto paysDto) {
        return paysRepository.findById(id)
                .map(pays -> {
                    pays.setNomPays(paysDto.getNomPays());
                    return PaysMapper.toDto(paysRepository.save(pays));
                })
                .orElse(null);
    }
}

