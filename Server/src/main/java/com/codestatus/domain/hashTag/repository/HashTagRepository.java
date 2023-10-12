package com.codestatus.domain.hashTag.repository;

import com.codestatus.domain.hashTag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository <HashTag, Long>, HashTagCustomRepository {
}
