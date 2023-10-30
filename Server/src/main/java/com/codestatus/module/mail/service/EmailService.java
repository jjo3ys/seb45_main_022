package com.codestatus.module.mail.service;

import com.codestatus.module.mail.entity.EmailCode;
import com.codestatus.domain.utils.email.CustomMailSender;
import com.codestatus.module.redis.repository.EmailCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final CustomMailSender customMailSender;
    private final EmailCodeRepository emailCodeRepository;

    public EmailCode sendJoinCode(EmailCode emailCode) {
        String code = randomCodeGenerator(); // 인증번호 생성
        customMailSender.sendAuthenticationCode(code, emailCode.getAddress());
        emailCode.setCode(code); // 인증번호 저장
        emailCodeRepository.save(emailCode);
        return emailCode;
    }

    // 문자열 랜덤 생성
    public String randomCodeGenerator() {

        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        int codeLength = 6;
        String numbers = "0123456789";

        for (int i = 0; i < codeLength; i++) {
            randomString.append(numbers.charAt(random.nextInt(numbers.length()))); // 8개의 알파벳 랜덤 생성
        }

        return randomString.toString(); // 문자열 반환
    }
}
