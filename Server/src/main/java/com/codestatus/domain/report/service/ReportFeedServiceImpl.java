package com.codestatus.domain.report.service;

import com.codestatus.domain.feed.command.FeedCommand;
import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.report.entity.ReportFeed;
import com.codestatus.domain.report.reporitory.ReportFeedRepository;
import com.codestatus.domain.user.entity.User;
import com.codestatus.global.exception.BusinessLogicException;
import com.codestatus.global.exception.ExceptionCode;
import com.codestatus.domain.utils.email.CustomMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ReportFeedServiceImpl implements ReportService<Feed>{
    @Value("${report.feed-alarm-count}")
    int feedAlarmCount;

    private final ReportFeedRepository reportFeedRepository;
    private final CustomMailSender customMailSender;
    private final FeedCommand feedCommand;

    @Override
    public void report(Feed targetFeed, User user) {
        targetFeed = feedCommand.findVerifiedFeed(targetFeed.getFeedId());

        if(reportFeedRepository.existsByFeedAndUser(targetFeed, user)) throw new BusinessLogicException(ExceptionCode.DUPLICATE_REPORT_EXCEPTION);

        ReportFeed reportFeed = new ReportFeed();
        reportFeed.setFeed(targetFeed);
        reportFeed.setUser(user);
        reportFeedRepository.save(reportFeed);

        sendReportAlarm(targetFeed);
    }

    @Override
    public void sendReportAlarm(Feed targetFeed) {
        int reportCount = reportFeedRepository.countAllByFeed(targetFeed);
        if (reportCount >= feedAlarmCount) {
            customMailSender.sendReportAlarm(targetFeed.getFeedId(), targetFeed.getUser().getNickname(), "피드");
        }
    }
}
