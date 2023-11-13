package com.codestatus.domain.user.repository;

import com.codestatus.domain.attendance.entity.Attendance;
import com.codestatus.domain.feed.dto.FeedDto;
import com.codestatus.domain.user.dto.UserDto;
import com.codestatus.domain.user.entity.User;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.codestatus.domain.feed.entity.QFeed.feed;
import static com.codestatus.domain.status.entity.QStat.stat;
import static com.codestatus.domain.status.entity.QStatus.status;
import static com.codestatus.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserDto.StatStatus> findByUserIdWithStatus(Attendance attendance) {
        return Optional.ofNullable(
                jpaQueryFactory.select(getStatStatus())
                        .from(user)
                        .innerJoin(user.statuses, status).on(status.stat.statId.eq((long) attendance.getStatId()))
                        .where(user.userId.eq(attendance.getUserId()))
                        .fetchOne()
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(user)
                        .leftJoin(user.roles).fetchJoin()
                        .where(user.email.eq(email))
                        .fetchOne()
        );
    }

    @Override
    public long countAllByNicknameContains(String nickname) {
        return jpaQueryFactory.selectFrom(user)
                .where(user.nickname.contains(nickname)).fetch().size();
    }

    @Override
    public boolean existsByNickname(String nickName) {
        return jpaQueryFactory.selectFrom(user)
                .where(user.nickname.eq(nickName))
                .fetchFirst() != null;
    }
    private ConstructorExpression<UserDto.StatStatus> getStatStatus() {
        return Projections.constructor(UserDto.StatStatus.class,
                user.userStatus,
                status
        );
    }
}
