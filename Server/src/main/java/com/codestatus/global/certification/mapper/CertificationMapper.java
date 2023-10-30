package com.codestatus.global.certification.mapper;

import com.codestatus.global.certification.dto.CertificationDto;
import com.codestatus.global.certification.entity.EmailCertification;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CertificationMapper {
    public EmailCertification certificationDtoToEmailCertification(CertificationDto certificationDto) {
        EmailCertification emailCertification = new EmailCertification();
        emailCertification.setEmail(certificationDto.getEmail());
        return emailCertification;
    }
}
