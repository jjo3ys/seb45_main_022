package com.codestatus.module.mail.command;

import com.codestatus.global.certification.dto.CertificationDto;
import com.codestatus.global.exception.BusinessLogicException;
import com.codestatus.global.exception.ExceptionCode;
import com.codestatus.module.mail.entity.EmailCode;
import com.codestatus.module.redis.repository.EmailCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EmailCommand {
    private final EmailCodeRepository emailCodeRepository;

    public void checkCode(CertificationDto certificationDto) {
        Optional<EmailCode> optionalEmailCode = emailCodeRepository.findById(certificationDto.getEmail());
        EmailCode emailCode = optionalEmailCode.orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_INPUT_VALUE));
        if (!emailCode.getCode().equals(certificationDto.getCode())) throw new BusinessLogicException(ExceptionCode.INVALID_INPUT_VALUE);
    }
}
