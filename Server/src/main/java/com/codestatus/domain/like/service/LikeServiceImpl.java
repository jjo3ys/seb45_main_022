package com.codestatus.domain.like.service;

import com.codestatus.domain.feed.command.FeedCommand;
import com.codestatus.domain.feed.dto.FeedDto;
import com.codestatus.domain.feed.mapper.FeedMapper;
import com.codestatus.domain.like.repository.LikeRepository;
import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.like.entity.Like;
import com.codestatus.domain.level.command.LevelCommand;
import com.codestatus.domain.user.entity.User;
import com.codestatus.domain.user.mapper.UserMapper;
import com.codestatus.global.exception.BusinessLogicException;
import com.codestatus.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    @Value("${exp.like-exp}")
    private int likeExp;

    private final FeedMapper feedMapper;
    private final UserMapper userMapper;

    private final LevelCommand levelCommand;
    private final FeedCommand feedCommand;
    private final LikeRepository likeRepository;
    @Override
    public boolean isLikeUser(long feedId, long userId) {
        return likeRepository.existsByFeedFeedAndUserAndDeletedIsFalse(
                feedMapper.feedIdToFeed(feedId),
                userMapper.userIdToUser(userId)
        );
    }

    @Override
    public void likeFeed(long feedId, long userId) {
        FeedDto.FeedForLikeDto feedForLikeDto = feedCommand.findVerifiedFeedWithFeedCategoryStatAndUser(feedId);
        User user = userMapper.userIdToUser(userId);
        if (feedForLikeDto.getUserId() == userId) throw new BusinessLogicException(ExceptionCode.LIKE_BAD_REQUEST);

        Optional<Like> optionalLike = likeRepository.findLikeByFeedFeedIdAndUserUserId(feedForLikeDto.getFeed().getFeedId(), userId);
        Like like;

        if (optionalLike.isPresent()) {
            like = optionalLike.get();
            like.setDeleted(false);
        } else {
            like = new Like();
            like.setFeed(feedForLikeDto.getFeed());
            like.setUser(user);

            levelCommand.gainExp(feedForLikeDto.getStatus(), likeExp);
        }
        likeRepository.save(like);
    }

    @Override
    public void disLikeFeed(long feedId, long userId) {
        Feed feed = feedCommand.findVerifiedFeed(feedId);
        User user = userMapper.userIdToUser(userId);
        Optional<Like> optionalLike = likeRepository.findLikeByFeedAndUser(feed, user);
        Like like = optionalLike.orElseThrow(() -> new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND));
        like.setDeleted(true);
        likeRepository.save(like);
    }
}
