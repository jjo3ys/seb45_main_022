package com.codestatus.module.redis.repository;

import com.codestatus.module.mail.entity.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCodeRepository extends CrudRepository<Email, String> {
}
