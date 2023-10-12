package com.codestatus.domain.like.repository;

import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.like.entity.Like;
import com.codestatus.domain.user.entity.User;

import java.util.Optional;

public interface LikeCustomRepository {
    Optional<Like> findLikeByFeedAndUser(Feed feed, User user);
    boolean existsByFeedFeedAndUserAndDeletedIsFalse(Feed feed, User user);
    long countAllByFeedAndDeletedIsFalse(Feed feed);
}