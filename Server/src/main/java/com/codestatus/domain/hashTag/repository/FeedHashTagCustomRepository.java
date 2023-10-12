package com.codestatus.domain.hashTag.repository;

import com.codestatus.domain.hashTag.entity.FeedHashTag;
import com.codestatus.domain.user.entity.User;

import java.util.List;

public interface FeedHashTagCustomRepository {
    List<FeedHashTag> findAllByFeedUser(User user);
    List<FeedHashTag> findAllByFeedId(long feedId);
}
