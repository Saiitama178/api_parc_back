package com.parc.api.controller;

import com.parc.api.model.dto.ImageDto;
import com.parc.api.model.entity.Image;
import com.parc.api.model.mapper.ImageMapper;
import com.parc.api.repository.ImageRepository;
import com.parc.api.repository.ParcRepository;
import com.parc.api.repository.TypeImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
