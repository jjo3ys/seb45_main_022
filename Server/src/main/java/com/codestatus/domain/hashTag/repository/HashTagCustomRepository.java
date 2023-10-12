package com.codestatus.domain.hashTag.repository;

import com.codestatus.domain.hashTag.entity.HashTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HashTagCustomRepository {
    Optional<HashTag> findHashTagByBody(String body);

    Page<HashTag> findHashTagByBodyLike(String body, Pageable pageable);
}
