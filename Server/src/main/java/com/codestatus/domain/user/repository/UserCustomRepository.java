package com.codestatus.domain.user.repository;

import com.codestatus.domain.attendance.entity.Attendance;
import com.codestatus.domain.user.dto.UserDto;
import com.codestatus.domain.user.entity.User;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<UserDto.StatStatus> findByUserIdWithStatus(Attendance attendance);
    Optional<User> findByEmail(String email);
    long countAllByNicknameContains(String nickname);
    boolean existsByNickname(String nickName);
}
