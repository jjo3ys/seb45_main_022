package com.codestatus.domain.status.repository;

import com.codestatus.domain.status.entity.Status;

import java.util.List;

public interface StatusCustomRepository {
    List<Status> findAllByUserUserId(long userId);
}
