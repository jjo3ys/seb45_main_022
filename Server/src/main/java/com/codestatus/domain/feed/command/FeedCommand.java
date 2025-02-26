package com.codestatus.domain.feed.command;

import com.codestatus.domain.feed.dto.FeedDto;
import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.feed.repository.FeedRepository;
import com.codestatus.global.exception.BusinessLogicException;
import com.codestatus.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Component
public class FeedCommand {
    private final FeedRepository feedRepository;

    @Transactional(readOnly = true)
    public Feed findVerifiedFeed(long feedId){
        return checkFeed(feedRepository.findById(feedId));

    }

    @Transactional(readOnly = true)
    public Feed findVerifiedFeedWithUser(long feedId) {
        return checkFeed(feedRepository.findByFeedIdWithUser(feedId));
    }

    @Transactional(readOnly = true)
    public FeedDto.FeedDetailDto findVerifiedFeedWithUserStatusesAndCategoryStat(long feedId) {
        return checkFeedDetail(feedRepository.findByFeedIdWithUserStatusesAndCategoryStat(feedId));
    }

    @Transactional(readOnly = true)
    public Feed findVerifiedFeedWithFeedCategoryStat(long feedId){
        return checkFeed(feedRepository.findByFeedIdWithFeedCategoryStat(feedId));
    }

    @Transactional(readOnly = true)
    public FeedDto.FeedForLikeDto findVerifiedFeedWithFeedCategoryStatAndUser(long feedId) {
        return checkFeedForLikeDto(feedRepository.findByFeedIdWithFeedCategoryStatAndUser(feedId));
    }

    public void deleteFeedAll(long userId) {
        List<Feed> feedList = feedRepository.findAllByUserId(userId);
        feedList.forEach(feed -> feed.setDeleted(true));
        feedRepository.saveAll(feedList);
    }

    private Feed checkFeed(Optional<Feed> optionalFeed) {
        Feed feed = optionalFeed.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND));
        if (feed.isDeleted()) {
            throw new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND);
        }

        return feed;
    }

    private FeedDto.FeedForLikeDto checkFeedForLikeDto(Optional<FeedDto.FeedForLikeDto> optionalFeed) {
        FeedDto.FeedForLikeDto feedForLikeDto = optionalFeed.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND));
        Feed feed = feedForLikeDto.getFeed();
        if (feed.isDeleted()) {
            throw new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND);
        }

        return feedForLikeDto;
    }

    private FeedDto.FeedDetailDto checkFeedDetail(Optional<FeedDto.FeedDetailDto> optionalFeed) {
        FeedDto.FeedDetailDto feed = optionalFeed.orElseThrow(() -> new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND));
        if (feed.getDeleted()) {
            throw new BusinessLogicException(ExceptionCode.FEED_NOT_FOUND);
        }

        return feed;
    }
}
