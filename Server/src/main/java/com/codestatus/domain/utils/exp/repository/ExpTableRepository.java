package com.codestatus.domain.utils.exp.repository;

import com.codestatus.domain.utils.exp.entity.Exp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpTableRepository extends JpaRepository<Exp, Long> {
}
