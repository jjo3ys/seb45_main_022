package com.codestatus.domain.like.service;

public interface LikeService {
    void likeFeed(long feedId, long userId);
    void disLikeFeed(long feedId, long userId);
    long feedLikeCount(long feedId);
}
