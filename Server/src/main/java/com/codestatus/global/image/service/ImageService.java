package com.codestatus.global.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadFeedImage(MultipartFile image);
}
