package com.codestatus.global.certification.service;

import com.codestatus.global.certification.dto.CertificationDto;
import com.codestatus.global.certification.mapper.CertificationMapper;
import com.codestatus.global.certification.repository.CertificationRepository;
import com.codestatus.module.mail.command.EmailCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CertificationServiceImpl implements CertificationService{
    private final EmailCommand emailCommand;
    private final CertificationMapper certificationMapper;
    private final CertificationRepository certificationRepository;
    @Override
    public void checkCode(CertificationDto certificationDto) {
        emailCommand.checkCode(certificationDto);
        certificationRepository.save(
                certificationMapper.certificationDtoToEmailCertification(certificationDto)
        );
    }
}
