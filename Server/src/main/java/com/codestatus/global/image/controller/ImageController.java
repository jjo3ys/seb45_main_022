package com.codestatus.global.image.controller;

import com.codestatus.global.image.dto.ImageResponseDto;
import com.codestatus.global.image.mapper.ImageMapper;
import com.codestatus.global.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final ImageMapper mapper;

    @PostMapping("/upload")
    public ResponseEntity uploadFeedImage(@RequestParam("image") MultipartFile image) {
        ImageResponseDto url = mapper.ImageUrlToResponseDto(imageService.uploadFeedImage(image));
        return ResponseEntity.ok().body(url);
    }
}
