package com.codestatus.global.certification.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@RedisHash(value = "emailCertification", timeToLive = 30*60)
public class EmailCertification {
    @Id
    private String email;
}
