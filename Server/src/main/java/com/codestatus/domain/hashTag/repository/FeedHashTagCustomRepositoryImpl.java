package com.codestatus.domain.hashTag.repository;

import com.codestatus.domain.hashTag.entity.FeedHashTag;
import com.codestatus.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.codestatus.domain.feed.entity.QFeed.feed;
import static com.codestatus.domain.hashTag.entity.QFeedHashTag.feedHashTag;
import static com.codestatus.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Repository
public class FeedHashTagCustomRepositoryImpl implements FeedHashTagCustomRepository{
    private final JPAQueryFactory jpaqueryFactory;

    @Override
    public List<FeedHashTag> findAllByFeedUser(User targetUser) {
        return jpaqueryFactory
                .selectFrom(feedHashTag)
                .leftJoin(feedHashTag.feed, feed)
                .join(feed.user, user)
                .on(
                        eqUser(targetUser)
                )
                .fetch();
    }

    @Override
    public List<FeedHashTag> findAllByFeedId(long feedId) {
        return jpaqueryFactory
                .selectFrom(feedHashTag)
                .join(feedHashTag.feed, feed)
                .on(
                        eqFeedId(feedId)
                ).fetch();
    }

    private BooleanExpression eqUser(User targetUser) {
        return targetUser != null ? user.eq(targetUser) : null;
    }
    private BooleanExpression eqFeedId(long feedId) {
        return feed.feedId.eq(feedId);
    }
}
