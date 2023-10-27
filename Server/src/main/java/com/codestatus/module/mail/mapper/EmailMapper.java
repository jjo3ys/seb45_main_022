package com.codestatus.module.mail.mapper;

import com.codestatus.module.mail.entity.Email;
import com.codestatus.module.mail.dto.EmailDto;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {
    public Email EmailDtoToEmail(EmailDto emailDto) {
        Email email = new Email();
        email.setAddress(emailDto.getAddress());
        return email;
    }
}
