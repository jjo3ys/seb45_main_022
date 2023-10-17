package com.codestatus.domain.attendance.repository;

import com.codestatus.domain.attendance.entity.Attendance;

import java.util.Optional;

public interface AttendanceCustomRepository {
    Optional<Attendance> findByUserId(long userId);
}
