package com.codestatus.module.mail.controller;

import com.codestatus.module.mail.dto.EmailDto;
import com.codestatus.module.mail.mapper.EmailMapper;
import com.codestatus.module.mail.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;
    private final EmailMapper emailMapper;

    public EmailController(EmailService emailService, EmailMapper emailMapper) {
        this.emailService = emailService;
        this.emailMapper = emailMapper;
    }

    @PostMapping("/join")
    public ResponseEntity sendJoinCode(@RequestBody EmailDto emailDto) {
        emailService.sendJoinCode(emailMapper.EmailDtoToEmail(emailDto));
        return ResponseEntity.ok().build();
    }
}
