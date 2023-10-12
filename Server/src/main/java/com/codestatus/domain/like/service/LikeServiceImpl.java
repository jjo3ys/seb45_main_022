package com.codestatus.domain.like.service;

import com.codestatus.domain.feed.command.FeedCommand;
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

    private final UserMapper userMapper;
    private final LevelCommand levelCommand;
    private final FeedCommand feedCommand;
    private final LikeRepository likeRepository;

    @Override
    public void likeFeed(long feedId, long userId) {
        Feed feed = feedCommand.findVerifiedFeedWithFeedCategoryStatAndUser(feedId);
        User user = userMapper.userIdToUser(userId);
        if (feed.getUser().getUserId() == userId) throw new BusinessLogicException(ExceptionCode.LIKE_BAD_REQUEST);

        Optional<Like> optionalLike = likeRepository.findLikeByFeedAndUser(feed, user);
        Like like;

        if (optionalLike.isPresent()) {
            like = optionalLike.get();
            like.setDeleted(false);
        } else {
            like = new Like();
            like.setFeed(feed);
            like.setUser(user);

            levelCommand.gainExp(feed.getUser(), likeExp, feed.getCategory().getStat().getStatId().intValue());
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

    @Override
    public long feedLikeCount(long feedId) {
        Feed feed = feedCommand.findVerifiedFeed(feedId);
        return likeRepository.countAllByFeedAndDeletedIsFalse(feed);
    }
}
