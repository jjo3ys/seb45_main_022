package com.codestatus.global.certification.controller;

import com.codestatus.global.certification.dto.CertificationDto;
import com.codestatus.global.certification.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/certification")
public class CertificationController {
    private final CertificationService certificationService;
    @PostMapping
    public ResponseEntity check(@RequestBody CertificationDto certificationDto){
        certificationService.checkCode(certificationDto);
        return ResponseEntity.ok().build();
    }
}
