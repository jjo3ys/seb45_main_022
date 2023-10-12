package com.codestatus.domain.user.repository;

import com.codestatus.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.codestatus.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
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
}
