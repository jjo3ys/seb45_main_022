package com.codestatus.domain.feed.mapper;

import com.codestatus.domain.category.entity.Category;
import com.codestatus.domain.feed.dto.*;
import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.hashTag.dto.HashTagResponseDto;
import com.codestatus.domain.hashTag.entity.FeedHashTag;
import com.codestatus.domain.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FeedMapper {

    default FeedResponseDto feedToFeedResponseDto(FeedDto.FeedDetailDto feed, boolean isLike, List<FeedHashTag> feedHashTags){
        return FeedResponseDto.builder()
                .feedId(feed.getFeedId())
                .nickname(feed.getNickname())
                .profileImage(feed.getProfileImage())
                .statId(feed.getStatId().intValue())
                .level(feed.getStatLevel())
                .data(feed.getData())
                .feedHashTags(feedHashTags.stream()
                        .map(feedHashTag -> HashTagResponseDto.builder()
                                .hashTagId(feedHashTag.getHashTag().getHashTagId())
                                .body(feedHashTag.getHashTag().getBody())
                                .build())
                        .collect(Collectors.toList()))
                .isLike(isLike)
                .likeCount(feed.getLikeCount().intValue())
                .createdAt(feed.getCreatedAt())
                .modifiedAt(feed.getModifiedAt())
                .build();
    }
    default List<FeedsResponseDto> feedsToFeedResponseDto(List<FeedDto.FeedListDto> feeds, List<Long> feedIds){
        return feeds.stream()
                .map(feed -> FeedsResponseDto
                        .builder()
                        .feedId(feed.getFeedId())
                        .nickname(feed.getNickname())
                        .profileImage(feed.getProfileImage())
                        .statId(feed.getStatId().intValue())
                        .level(feed.getStatLevel())
                        .body(feed.getBody())
                        .isLike(feedIds.contains(feed.getFeedId()))
                        .likeCount(feed.getLikeCount().intValue())
                        .commentCount(feed.getCommentCount().intValue())
                        .createdAt(feed.getCreatedAt())
                        .modifiedAt(feed.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
    default Feed feedPostDtoToFeed(Category category, FeedPostDto requestBody, User user){
        Feed feed = new Feed();
        feed.setBody(requestBody.getBody());
        feed.setData(requestBody.getData());
        feed.setCategory(category);
        feed.setUser(user);
        return feed;
    }

    Feed feedPatchDtoToFeed(FeedPatchDto feedPatchDto);
    default Feed feedIdToFeed(Long feedId){
        Feed feed = new Feed();
        feed.setFeedId(feedId);
        return feed;
    }
}
