package com.codestatus.domain.status.repository;

import com.codestatus.domain.status.entity.Status;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.codestatus.domain.status.entity.QStatus.status;


@RequiredArgsConstructor
@Repository
public class StatusCustomRepositoryImpl implements StatusCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Status> findAllByUserUserId(long userId) {
        return jpaQueryFactory.selectFrom(status)
                .where(status.user.userId.eq(userId))
                .fetch();
    }
}
