package com.codestatus.domain.feed.service;

import com.codestatus.domain.feed.dto.FeedDto;
import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.auth.dto.PrincipalDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface FeedService {
    void createEntity(Feed feed);
    Feed findEntity(long feedId);
    Page<FeedDto.FeedListDto> findAllFeedByCategory(long categoryId, int page, int size);
    Page<Feed> findAllWeeklyBestFeeds(long categoryId, int page, int size);
    Page<Feed> findAllFeedByBodyAndCategory(long categoryId, String text, int page, int size);
    Page<FeedDto.FeedListDto> findAllFeedByUserAndCategory(long categoryId, String text, int page, int size);
    Page<Feed> findFeedByHashTagAndCategory(long categoryId, long hashTagId, int page, int size);
    Page<Feed> findFeedByHashTagBody(long categoryId, String body, int page, int size);
    Page<Feed> findAllFeedByDeleted(int page, int size);
    void updateEntity(Feed feed, long userId);
    Page<Feed> userPost(long userId, int page, int size);
    void deleteEntity(long feedId, long userId);
    boolean isLikeUser(long feedId, long userId);
    List<Long> isLikeFeedIds(List<Feed> feeds, PrincipalDto principal);
    Page<Feed> findAllEntity(int page, int size);
    int getStatId(long feedId);
}
