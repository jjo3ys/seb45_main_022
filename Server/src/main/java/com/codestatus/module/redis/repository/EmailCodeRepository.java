package com.codestatus.module.redis.repository;

import com.codestatus.module.mail.entity.EmailCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCodeRepository extends CrudRepository<EmailCode, String> {
}
