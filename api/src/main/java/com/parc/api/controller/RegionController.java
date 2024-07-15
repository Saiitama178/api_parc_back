package com.parc.api.controller;

import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.dto.RegionDto;
import com.parc.api.model.dto.RegionDto;
import com.parc.api.model.entity.Pays;
import com.parc.api.model.entity.Region;
import com.parc.api.model.mapper.PaysMapper;
import com.parc.api.model.mapper.RegionMapper;
import com.parc.api.repository.PaysRepository;
import com.parc.api.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RegionController {
    private final RegionRepository regionRepository;
    //@CrossOrigin(origins = "http//localhost:3308")
    @GetMapping("/region")
    public ResponseEntity<List<RegionDto>> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        List<RegionDto> regionDto = regions.stream()
                .map(RegionMapper::toDto).toList();
        return ResponseEntity.ok(regionDto);
    }
    //@CrossOrigin(origins = "http:localhost:3308")
    @CrossOrigin(origins = "http:localhost:3308")
    @GetMapping("/region/{id}")
    public ResponseEntity<RegionDto> getRegionById(@PathVariable Integer id) {
        Optional<Region> region = regionRepository.findById(id);
        if (region.isPresent()) {
            RegionDto regionDto = RegionMapper.toDto(region.get());
            return ResponseEntity.ok(regionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
        //@CrossOrigin(origins = "http:localhost:3308")
    }
    //chatGpt
    @Autowired
    private PaysRepository paysRepository;
    @PostMapping("/region/{idPays}")
    public ResponseEntity<RegionDto> createRegionByPays(@RequestBody RegionDto regionDto, @PathVariable("idPays") int idPays) throws Exception  {
        Pays pays = paysRepository.findById(idPays)
        .orElseThrow(()-> new Exception("Erreur"));
        Region region = RegionMapper.toEntity(regionDto, pays);
        Region savedRegion = regionRepository.save(region);
        RegionDto savedRegionDto = RegionMapper.toDto(savedRegion);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegionDto);
    }


@DeleteMapping("/region/{id}")
//Interger Long
public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
    Optional<Region> regionOptional = regionRepository.findById(id);
    if (regionOptional.isPresent()) {
        regionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}
@CrossOrigin(origins = "http:localhost:3308")
@PutMapping("/region/{id}")
public ResponseEntity<RegionDto> updateRegion(@PathVariable Integer id, @RequestBody RegionDto regionDto) {
    Optional<Region> foundRegionOptional = regionRepository.findById(id);
    if (foundRegionOptional.isPresent()) {
        Region foundRegion= foundRegionOptional.get();
        foundRegion.setNomRegion(regionDto.getNomRegion());
        Region savedRegion= regionRepository.save(foundRegion);
        RegionDto updatedRegionDto = RegionMapper.toDto(savedRegion);
        return ResponseEntity.ok(updatedRegionDto);
    } else {
        return ResponseEntity.notFound().build();
    }
}
    }


