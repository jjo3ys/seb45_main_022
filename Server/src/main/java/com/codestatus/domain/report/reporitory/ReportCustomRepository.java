package com.codestatus.domain.report.reporitory;

import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.user.entity.User;

public interface ReportCustomRepository {
    int countAllByFeed(Feed feed);
    boolean existsByFeedAndUser(Feed feed, User user);
}
