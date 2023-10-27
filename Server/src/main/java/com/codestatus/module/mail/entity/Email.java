package com.codestatus.module.mail.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value = "emailCode", timeToLive = 180)
public class Email {
    @Id
    private String address;
    private String code;
}
