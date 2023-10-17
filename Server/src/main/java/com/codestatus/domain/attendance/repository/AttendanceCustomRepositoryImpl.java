package com.codestatus.domain.attendance.repository;

import com.codestatus.domain.attendance.entity.Attendance;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.codestatus.domain.attendance.entity.QAttendance.attendance;

@RequiredArgsConstructor
@Repository
public class AttendanceCustomRepositoryImpl implements AttendanceCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Optional<Attendance> findByUserId(long userId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(attendance)
                        .where(eqUserId(userId))
                        .fetchOne()
        );
    }
    private BooleanExpression eqUserId(long userId){
        return attendance.userId.eq(userId);
    }
}
