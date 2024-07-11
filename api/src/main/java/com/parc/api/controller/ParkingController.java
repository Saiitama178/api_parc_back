package com.parc.api.controller;

import com.parc.api.model.dto.ParkingDto;
import com.parc.api.model.entity.Parking;
import com.parc.api.model.mapper.ParkingMapper;
import com.parc.api.repository.ParkingRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parking")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingController {

    private ParkingRepository parkingRepository;

    @GetMapping
    public ResponseEntity<List<ParkingDto>> getAllParc() {
        // Récupérer tous les parkings de la base de données
        List<Parking> parkingList = parkingRepository.findAll();
        // Convertir la liste des entités Parking en liste de DTOs ParkingDto
        List<ParkingDto> parkingDtoList = parkingList.stream()
                .map(ParkingMapper::toDto).toList();
        // Retourner la réponse HTTP avec la liste des Parkings
        return ResponseEntity.ok(parkingDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> getById(@PathVariable int id) {
        // Rechercher le parking par son ID
        return parkingRepository.findById(id)
                .map(parking -> ResponseEntity.ok(ParkingMapper.toDto(parking)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParkingDto> createParking(@RequestBody ParkingDto parkingDto) {
        Parking parking = ParkingMapper.toEntity(parkingDto);
        parkingRepository.save(parking);
        return ResponseEntity.ok(ParkingMapper.toDto(parking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDto> updateParking(@PathVariable int id, @RequestBody ParkingDto parkingDto) {
        // Rechercher le Parking par son ID
        Optional<Parking> existingParking = parkingRepository.findById(id);
        if (existingParking.isPresent()) {
            // Mettre à jour les informations du Parking
            Parking parking = existingParking.get();
            parking.setLibParking(parkingDto.getLibParking());
            // Sauvegarder les modifications dans la base de donnée
            Parking updatedParking = parkingRepository.save(parking);
            // Retourner une requête HTTP avec le ParkingDto mis à jour
            return ResponseEntity.ok(ParkingMapper.toDto(updatedParking));
        } else {
            // Retourner une réponse 404 quand cela échoue
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable int id) {
        try {
            // Rechercher si le Parking existe par son ID
            Optional<Parking> existingParking = parkingRepository.findById(id);
            if (existingParking.isPresent()) {
                Parking parking = existingParking.get();
                // Supprimer le Parking trouvé de la base de donnée
                parkingRepository.delete(parking);
                // Retourner une réponse
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
