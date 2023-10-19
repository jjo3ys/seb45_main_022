package com.codestatus.domain.feed.dto;

import lombok.*;

import java.time.LocalDateTime;

public class FeedDto {
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
}
