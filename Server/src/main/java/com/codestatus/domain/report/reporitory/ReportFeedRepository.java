package com.codestatus.domain.report.reporitory;

import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.user.entity.User;
import com.codestatus.domain.report.entity.ReportFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportFeedRepository extends JpaRepository<ReportFeed, Long> {
    int countAllByFeed(Feed feed);
    Optional<ReportFeed> findByFeedAndUser(Feed feed, User user);
}
