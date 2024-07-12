package com.parc.api.controller;

import com.parc.api.model.dto.ImageDto;
import com.parc.api.model.entity.Image;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.TypeImage;
import com.parc.api.model.mapper.ImageMapper;
import com.parc.api.repository.ImageRepository;
import com.parc.api.repository.ParcRepository;
import com.parc.api.repository.TypeImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {

    private ImageRepository imageRepository;
    private final ParcRepository parcRepository;
    private final TypeImageRepository typeImageRepository;

    @GetMapping("/image")
    public ResponseEntity<List<ImageDto>> getAllImages() {
        List<Image> imageList = imageRepository.findAll();
        List<ImageDto> imageDtoList = imageList.stream()
                .map(ImageMapper::toDto).toList();
        return ResponseEntity.ok(imageDtoList);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<ImageDto> getImageById(@PathVariable int id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            ImageDto imageDto = ImageMapper.toDto(imageOptional.get());
            return ResponseEntity.ok(imageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/image/{idTypeImage}/{idParc}")
    public ResponseEntity<ImageDto> createImage(@RequestBody ImageDto imageDto, @PathVariable("idTypeImage") int idTypeImage, @PathVariable("idParc") int idParc) throws Exception {
        TypeImage typeImage = typeImageRepository.findById(idTypeImage)
                .orElseThrow(()-> new Exception("erreur"));
        Parc parc = parcRepository.findById(idParc)
                .orElseThrow(()-> new Exception("erreur"));
        Image image = ImageMapper.toEntity(imageDto, typeImage, parc);
        Image savedImage = imageRepository.save(image);
        ImageDto savedImageDto = ImageMapper.toDto(savedImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImageDto);
    }

    @PutMapping("/image/{id}")
    public ResponseEntity<ImageDto> updateImage(@PathVariable int id, @RequestBody ImageDto imageDto) {
        Optional<Image> foundImageOptional = imageRepository.findById(id);
        if (foundImageOptional.isPresent()) {
            Image foundImage = foundImageOptional.get();
            foundImage.setRefImage(imageDto.getRefImage());
            Image savedImage = imageRepository.save(foundImage);
            ImageDto updatedImageDto = ImageMapper.toDto(savedImage);
            return ResponseEntity.ok(updatedImageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<ImageDto> deleteImage(@PathVariable int id) {
        Optional<Image> ImageOptional = imageRepository.findById(id);
        if (ImageOptional.isPresent()) {
            imageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
