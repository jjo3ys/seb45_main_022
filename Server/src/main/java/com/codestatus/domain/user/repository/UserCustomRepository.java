package com.codestatus.domain.user.repository;

import com.codestatus.domain.user.entity.User;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<User> findByEmail(String email);
    long countAllByNicknameContains(String nickname);
    boolean existsByNickname(String nickName);
}
