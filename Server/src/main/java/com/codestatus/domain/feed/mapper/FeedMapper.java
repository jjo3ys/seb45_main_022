package com.codestatus.domain.feed.mapper;

import com.codestatus.domain.category.entity.Category;
import com.codestatus.domain.feed.dto.*;
import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.hashTag.dto.HashTagResponseDto;
import com.codestatus.domain.hashTag.entity.FeedHashTag;
import com.codestatus.domain.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FeedMapper {

    default FeedResponseDto feedToFeedResponseDto(Feed feed, boolean isLike, List<FeedHashTag> feedHashTags, long likeCount){
        return FeedResponseDto.builder()
                .feedId(feed.getFeedId())
                .nickname(feed.getUser().getNickname())
                .profileImage(feed.getUser().getProfileImage())
                .statId(feed.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue() - 1).getStat().getStatId().intValue())
                .level(feed.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue() - 1).getStatLevel())
                .data(feed.getData())
                .feedHashTags(feedHashTags.stream()
                        .map(feedHashTag -> HashTagResponseDto.builder()
                                .hashTagId(feedHashTag.getHashTag().getHashTagId())
                                .body(feedHashTag.getHashTag().getBody())
                                .build())
                        .collect(Collectors.toList()))
                .isLike(isLike)
                .likeCount((int) likeCount)
                .createdAt(feed.getCreatedAt())
                .modifiedAt(feed.getModifiedAt())
                .build();
    }

    default List<FeedsResponseDto> feedsToFeedResponseDtos(List<Feed> feeds, List<Long> feedIds){
        return feeds.stream()
                .map(feed -> FeedsResponseDto
                        .builder()
                        .feedId(feed.getFeedId())
                        .nickname(feed.getUser().getNickname())
                        .profileImage(feed.getUser().getProfileImage())
                        .statId(feed.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue() - 1).getStat().getStatId().intValue())
                        .level(feed.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue() - 1).getStatLevel())
                        .body(feed.getBody())
                        .feedHashTags(feed.getFeedHashTags().stream()
                                .map(feedHashTag -> HashTagResponseDto.builder()
                                        .hashTagId(feedHashTag.getHashTag().getHashTagId())
                                        .body(feedHashTag.getHashTag().getBody())
                                        .build())
                                .collect(Collectors.toList()))
                        .isLike(feedIds.contains(feed.getFeedId()))
                        .likeCount((int)feed.getLikes().stream().filter(like -> !like.getDeleted()).count())
                        .commentCount((int) feed.getComments().stream()
                                .filter(comment -> !comment.isDeleted())
                                .count())
                        .createdAt(feed.getCreatedAt())
                        .modifiedAt(feed.getModifiedAt())
                        .build())
                .collect(Collectors.toList());
    }
    default List<FeedsResponseDto> feedsToFeedResponseDto(List<FeedDto.FeedListDto> feeds, List<Long> feedIds){
        return feeds.stream()
                .map(feed -> FeedsResponseDto
                        .builder()
                        .feedId(feed.getFeedId())
                        .nickname(feed.getNickname())
                        .profileImage(feed.getProfileImage())
                        .statId(feed.getStatId().intValue())
                        .level(feed.getStatus().getStatLevel())
                        .body(feed.getBody())
                        .feedHashTags(feed.getFeedHashTags())
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
