package com.codestatus.domain.feed.mapper;

import com.codestatus.domain.category.entity.Category;
import com.codestatus.domain.comment.dto.CommentResponseDto;
import com.codestatus.domain.feed.dto.FeedPatchDto;
import com.codestatus.domain.feed.dto.FeedPostDto;
import com.codestatus.domain.feed.dto.FeedResponseDto;
import com.codestatus.domain.feed.dto.FeedsResponseDto;
import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.hashTag.dto.HashTagResponseDto;
import com.codestatus.domain.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FeedMapper {

    default FeedResponseDto feedToFeedResponseDto(Feed feed){
        return FeedResponseDto.builder()
                .feedId(feed.getFeedId())
                .nickName(feed.getUser().getNickName())
                .profileImage(feed.getUser().getProfileImage())
                .statId(feed.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue() - 1).getStat().getStatId().intValue())
                .level(feed.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue() - 1).getStatLevel())
                .body(feed.getBody())
                .feedHashTags(feed.getFeedHashTags().stream()
                        .map(feedHashTag -> HashTagResponseDto.builder()
                                .hashTagId(feedHashTag.getHashTag().getHashTagId())
                                .count(feedHashTag.getHashTag().getCount())
                                .body(feedHashTag.getHashTag().getBody())
                                .build())
                        .collect(Collectors.toList()))
                .likeCount(feed.getLikes().size())
                .comments(feed.getComments().stream()
                        .map(comment -> CommentResponseDto.builder()
                                .commentId(comment.getCommentId())
                                .nickName(comment.getUser().getNickName())
                                .profileImage(comment.getUser().getProfileImage())
                                .level(comment.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue()).getStatLevel())
                                .body(comment.getBody())
                                .createDate(comment.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(feed.getCreatedAt())
                .modifiedAt(feed.getModifiedAt())
                .build();
    }

    default List<FeedsResponseDto> feedsToFeedResponseDtos(List<Feed> feeds){
        return feeds.stream()
                .map(feed -> FeedsResponseDto
                        .builder()
                        .feedId(feed.getFeedId())
                        .nickName(feed.getUser().getNickName())
                        .profileImage(feed.getUser().getProfileImage())
                        .statId(feed.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue() - 1).getStat().getStatId().intValue())
                        .level(feed.getUser().getStatuses().get(feed.getCategory().getStat().getStatId().intValue() - 1).getStatLevel())
                        .body(feed.getBody())
                        .feedHashTags(feed.getFeedHashTags().stream()
                                .map(feedHashTag -> HashTagResponseDto.builder()
                                        .hashTagId(feedHashTag.getHashTag().getHashTagId())
                                        .count(feedHashTag.getHashTag().getCount())
                                        .body(feedHashTag.getHashTag().getBody())
                                        .build())
                                .collect(Collectors.toList()))
                        .likeCount(feed.getLikes().size())
                        .commentCount(feed.getComments().size())
                        .createdAt(feed.getCreatedAt())
                        .modifiedAt(feed.getModifiedAt())
                        .build())
                .collect(Collectors.toList());

    }
    default Feed feedPostDtoToFeed(Category category, FeedPostDto requestBody, User user){
        Feed feed = new Feed();
        feed.setBody(requestBody.getBody());
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
