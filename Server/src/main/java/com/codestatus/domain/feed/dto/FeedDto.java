package com.codestatus.domain.feed.dto;

import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.status.entity.Status;
import com.codestatus.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

public class FeedDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedDetailDto {
        private Long feedId;
        private String data;
        private Long userId;
        private String nickname;
        private String profileImage;
        private Long statId;
        private Integer statLevel;
        private Long likeCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Boolean deleted;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedListDto {
        private Long feedId;
        private String body;
        private Long statId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        private Long userId;
        private String nickname;
        private String profileImage;
        private Integer statLevel;

        private Long likeCount;
        private Long commentCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedForLikeDto {
        private Feed feed;

        private Long statId;

        private Long userId;
        private Status status;
    }
}
