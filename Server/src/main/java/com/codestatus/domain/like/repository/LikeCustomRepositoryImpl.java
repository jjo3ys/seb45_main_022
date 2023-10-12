package com.codestatus.domain.like.repository;

import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.like.entity.Like;
import com.codestatus.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.codestatus.domain.like.entity.QLike.like;

@RequiredArgsConstructor
@Repository
public class LikeCustomRepositoryImpl implements LikeCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Optional<Like> findLikeByFeedAndUser(Feed feed, User user) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(like)
                .where(
                        eqFeed(feed),
                        eqUser(user)
                )
                .fetchOne());
    }

    @Override
    public boolean existsByFeedFeedAndUserAndDeletedIsFalse(Feed feed, User user) {
        return jpaQueryFactory
                .selectFrom(like)
                .where(
                        eqFeed(feed),
                        eqUser(user),
                        notDeleted()
                ).fetchOne() != null;
    }

    @Override
    public long countAllByFeedAndDeletedIsFalse(Feed feed) {
        return jpaQueryFactory
                .selectFrom(like)
                .where(
                        eqFeed(feed),
                        notDeleted()
                ).fetch().size();
    }

    private BooleanExpression eqFeed(Feed feed) {
        return feed != null ? like.feed.eq(feed) : null;
    }
    private BooleanExpression eqUser(User user) {
        return user != null? like.user.eq(user) : null;
    }

    private BooleanExpression notDeleted() {
        return like.deleted.eq(false);
    }
}
