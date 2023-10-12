package com.codestatus.domain.report.reporitory;

import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.codestatus.domain.report.entity.QReportFeed.reportFeed;

@RequiredArgsConstructor
@Repository
public class ReportCustomRepositoryImpl implements ReportCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public int countAllByFeed(Feed feed) {
        return jpaQueryFactory
                .select(reportFeed.count())
                .from(reportFeed)
                .where(reportFeed.feed.eq(feed))
                .fetch().size();
    }

    @Override
    public boolean existsByFeedAndUser(Feed feed, User user) {
        return jpaQueryFactory
                .selectFrom(reportFeed)
                .where(reportFeed.feed.eq(feed)
                        .and(reportFeed.user.eq(user)))
                .fetchOne() != null;
    }
}
