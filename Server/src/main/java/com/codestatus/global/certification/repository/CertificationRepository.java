package com.codestatus.global.certification.repository;

import com.codestatus.global.certification.entity.EmailCertification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends CrudRepository<EmailCertification, String> {
}
