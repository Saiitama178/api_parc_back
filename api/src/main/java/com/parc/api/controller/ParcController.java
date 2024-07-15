package com.parc.api.controller;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Parking;
import com.parc.api.model.entity.Ville;
import com.parc.api.model.mapper.ParcMapper;
import com.parc.api.repository.ParcRepository;
import com.parc.api.repository.ParkingRepository;
import com.parc.api.repository.VilleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ParcController {

    private final ParcRepository parcRepository;
    private final ParkingRepository parkingRepository;
    private final VilleRepository villeRepository;



    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/parc")
    @Operation(summary = "Affiche la liste des parcs", description = "Retourne une liste de parc",
            responses = {
                    @ApiResponse(responseCode = "200", description = " Liste Parc")
            })
    public ResponseEntity<List<ParcDto>> getAllParc() {
        List<Parc> parcList = parcRepository.findAll();
        List<ParcDto> parcDtoList = parcList.stream()
                .map(ParcMapper::toDto).toList();
        return ResponseEntity.ok(parcDtoList);
    }

    @GetMapping("/parc/{nomParc}")
    public ResponseEntity<ParcDto> getNomParc(@PathVariable String nomParc) {
        return parcRepository.findParcByNomParc(nomParc)
                .map(parc -> ResponseEntity.ok(ParcMapper.toDto(parc)))
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping("/parc/{idParking}/{idVille}")
    public ResponseEntity<ParcDto> createParc(@RequestBody ParcDto parcDto,@PathVariable("idParking") int idParking, @PathVariable("idVille") int idVille) throws Exception {
        Parking parking = parkingRepository.findById(idParking)
                .orElseThrow(()-> new Exception("erreur"));
        Ville ville = villeRepository.findById(idVille)
                .orElseThrow(()-> new Exception("erreur"));
        Parc parc = ParcMapper.toEntity(parcDto, parking, ville);
        Parc savedParc = parcRepository.save(parc);
        ParcDto savedParcDto = ParcMapper.toDto(savedParc);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParcDto);
    }

    @DeleteMapping("/parc/{id}")
    public ResponseEntity<Void> deleteParc(@PathVariable Integer id) {
        Optional<Parc> paysOptional = parcRepository.findById(id);
        if (paysOptional.isPresent()) {
            parcRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcDto> updateParc(@PathVariable Integer id, @RequestBody ParcDto parcDto) {
        // Rechercher le parc par son ID
        Optional<Parc> existingParc = parcRepository.findById(id);
        if (existingParc.isPresent()) {
            // Mettre à jour les informations du parc
            Parc parc = existingParc.get();
            parc.setNomParc(parcDto.getNomParc());
            parc.setPresentation(parcDto.getPresentation());
            parc.setAdresse(parcDto.getAdresse());
            parc.setLatitudeParc(parcDto.getLatitudeParc());
            parc.setLongitudeParc(parcDto.getLongitudeParc());
            parc.setSiteInternet(parcDto.getSiteInternet());
            parc.setNumeroTelParc(parcDto.getNumeroTelParc());
            parc.setIsRestauration(parcDto.getIsRestauration());
            parc.setIsBoutique(parcDto.getIsBoutique());
            parc.setIsSejour(parcDto.getIsSejour());
            parc.setIsTransportCommun(parcDto.getIsTransportCommun());
            parc.setUrlAffilation(parcDto.getUrlAffilation());
            // Sauvegarder les modifications dans la base de données
            parc = parcRepository.save(parc);
            // Retourner la réponse HTTP avec le ParcDto mis à jour
            return ResponseEntity.ok(ParcMapper.toDto(parc));
        } else {
            // Retourner une réponse 404 si le parc n'est pas trouvé
            return ResponseEntity.notFound().build();
        }
    }
}

