package com.codestatus.domain.hashTag.command;

import com.codestatus.domain.hashTag.entity.FeedHashTag;
import com.codestatus.domain.hashTag.repository.FeedHashTagRepository;
import com.codestatus.domain.user.entity.User;
import com.codestatus.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class FeedHashTagCommand {
    private final UserMapper userMapper;
    private final FeedHashTagRepository feedHashTagRepository;

    public void createFeedHashtags(List<FeedHashTag> feedHashTags){
        feedHashTagRepository.saveAll(feedHashTags);
    }

    @Transactional(readOnly = true)
    public List<FeedHashTag> getFeedHasTags(long feedId) {
        return feedHashTagRepository.findAllByFeedId(feedId);
    }

    public void deleteFeedHashtagAll(long userId) {
        User user = userMapper.userIdToUser(userId);
        List<FeedHashTag> feedHashTagList = feedHashTagRepository.findAllByFeedUser(user);
        deleteFeedHashtagAll(feedHashTagList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteFeedHashtagAll(List<FeedHashTag> feedHashTagList) {
        feedHashTagRepository.deleteAll(feedHashTagList);
    }
}
