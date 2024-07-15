package com.parc.api.controller;

import com.parc.api.model.dto.VilleDto;
import com.parc.api.model.entity.Region;
import com.parc.api.model.entity.Ville;
import com.parc.api.model.mapper.VilleMapper;
import com.parc.api.repository.RegionRepository;
import com.parc.api.repository.VilleRepository;
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
//@RequestMapping("/api")
public class VilleController {
    private final VilleRepository villeRepository;
    private final RegionRepository regionRepository;;

    @GetMapping("/ville")
    public ResponseEntity<List<VilleDto>> getAllVilles() {
        List<Ville> villes = villeRepository.findAll();
        List<VilleDto> villeDto = villes.stream()
                .map(VilleMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(villeDto);
    }

    @GetMapping("/ville/{id}")
    public ResponseEntity<VilleDto> getVilleById(@PathVariable Integer id) {
        Optional<Ville> ville = villeRepository.findById(id);
        if (ville.isPresent()) {
            VilleDto villeDto = VilleMapper.toDto(ville.get());
            return ResponseEntity.ok(villeDto);
        } else {
            return ResponseEntity.notFound().build();
        }}

    @DeleteMapping("/ville/{id}")
    public ResponseEntity<Void> deleteVille(@PathVariable Integer id) {
        Optional<Ville> villeOptional = villeRepository.findById(id);
        if (villeOptional.isPresent()) {
            villeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin(origins = "http:localhost:3308")
    @PutMapping("/ville/{id}")
    public ResponseEntity<VilleDto> updateVille(@PathVariable Integer id, @RequestBody VilleDto villeDto) {
        Optional<Ville> foundVilleOptional = villeRepository.findById(id);
        if (foundVilleOptional.isPresent()) {
            Ville foundVille= foundVilleOptional.get();
            foundVille.setNomVille(villeDto.getNomVille());
            foundVille.setCodePostal(villeDto.getCodePostal());
            Ville savedVille= villeRepository.save(foundVille);
            VilleDto updatedVilleDto = VilleMapper.toDto(savedVille);
            return ResponseEntity.ok(updatedVilleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ville/{idRegion}")
    public ResponseEntity<VilleDto> createVillebyRegion(@RequestBody VilleDto villeDto,@PathVariable("idRegion") int idRegion) throws Exception{
        Region region  = regionRepository.findById(idRegion)
                .orElseThrow(()-> new Exception("Erreur"));
        Ville ville = VilleMapper.toEntity(villeDto, region);
        Ville savedVille = villeRepository.save(ville);
        VilleDto savedVilleDto = VilleMapper.toDto(savedVille);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVilleDto);
        }

    }



