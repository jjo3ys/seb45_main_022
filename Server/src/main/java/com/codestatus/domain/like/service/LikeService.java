package com.codestatus.domain.like.service;

public interface LikeService {
    boolean isLikeUser(long feedId, long userId);
    void likeFeed(long feedId, long userId);
    void disLikeFeed(long feedId, long userId);
}
