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
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingController {

    private ParkingRepository parkingRepository;

    @GetMapping("/parking")
    public ResponseEntity<List<ParkingDto>> getAllParking() {
        List<Parking> parkingList = parkingRepository.findAll();
        List<ParkingDto> parkingDtoList = parkingList.stream()
                .map(ParkingMapper::toDto).toList();
        return ResponseEntity.ok(parkingDtoList);
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<ParkingDto> getParkingById(@PathVariable int id) {
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isPresent()) {
            ParkingDto parkingDto = ParkingMapper.toDto(parking.get());
            return ResponseEntity.ok(parkingDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/parking")
    public ResponseEntity<ParkingDto> createParking(@RequestBody ParkingDto parkingDto) {
        Parking parking = ParkingMapper.toEntity(parkingDto);
        Parking savedParking = parkingRepository.save(parking);
        ParkingDto savedParkingDto = ParkingMapper.toDto(savedParking);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParkingDto);
    }

    @PutMapping("/parking/{id}")
    public ResponseEntity<ParkingDto> updateParking(@PathVariable int id, @RequestBody ParkingDto parkingDto) {
        Optional<Parking> foundParkingOptional = parkingRepository.findById(id);
        if (foundParkingOptional.isPresent()) {
            Parking foundParking = foundParkingOptional.get();
            foundParking.setLibParking(parkingDto.getLibParking());
            Parking savedParking = parkingRepository.save(foundParking);
            ParkingDto updatedParkingDto = ParkingMapper.toDto(savedParking);
            return ResponseEntity.ok(updatedParkingDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/parking/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable int id) {
        Optional<Parking> ParkingOptional = parkingRepository.findById(id);
        if (ParkingOptional.isPresent()) {
            parkingRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
