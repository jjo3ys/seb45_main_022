package com.codestatus.module.mail.mapper;

import com.codestatus.module.mail.entity.EmailCode;
import com.codestatus.module.mail.dto.EmailDto;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {
    public EmailCode EmailDtoToEmail(EmailDto emailDto) {
        EmailCode emailCode = new EmailCode();
        emailCode.setAddress(emailDto.getAddress());
        return emailCode;
    }
}
