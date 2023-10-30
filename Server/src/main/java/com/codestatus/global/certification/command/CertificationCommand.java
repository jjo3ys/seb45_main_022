package com.codestatus.global.certification.command;

import com.codestatus.global.certification.entity.EmailCertification;
import com.codestatus.global.certification.repository.CertificationRepository;
import com.codestatus.global.exception.BusinessLogicException;
import com.codestatus.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CertificationCommand {
    private final CertificationRepository certificationRepository;

    public void checkCertificated(String email) {
        Optional<EmailCertification> optionalEmailCertification = certificationRepository.findById(email);
        optionalEmailCertification.orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_USER));
    }
}
