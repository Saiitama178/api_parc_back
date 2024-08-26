package com.parc.api.service;

import com.parc.api.model.dto.PaysDto;

import com.parc.api.model.entity.Pays;
import com.parc.api.model.mapper.PaysMapper;
import com.parc.api.repository.PaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor

public class PaysService {
    private final PaysRepository paysRepository;
    public List<PaysDto> getAllPays() {
        List<Pays> paysList = paysRepository.findAll();
        return paysList.stream()
                .map(PaysMapper::toDto)
                .collect(Collectors.toList());
    }
    public Optional<PaysDto> getPaysById(Integer id) {
        Optional<Pays> pays = paysRepository.findById(id);
        return pays.map(PaysMapper::toDto);
    }
    //240826
    public PaysDto createPays(PaysDto paysDto) {
        Pays pays = PaysMapper.toEntity(paysDto);
        Pays savedPays = paysRepository.save(pays);
        return PaysMapper.toDto(savedPays);
    }
    //
    public void deletePays(Integer id) {
        paysRepository.deleteById(id);
    }
    //
    public PaysDto updatePays(Integer id, PaysDto paysDto) {
        return paysRepository.findById(id)
                .map(pays -> {
                    pays.setNomPays(paysDto.getNomPays());
                    return PaysMapper.toDto(paysRepository.save(pays));
                })
                .orElse(null);
    }



}
