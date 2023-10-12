package com.codestatus.domain.like.likeCommand;

import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.like.repository.LikeRepository;
import com.codestatus.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class LikeCommand {

    private final LikeRepository likeRepository;

    public boolean checkIsLikeUser(Feed feed, User user){
        return likeRepository.existsByFeedFeedAndUserAndDeletedIsFalse(feed, user);
    }

}
