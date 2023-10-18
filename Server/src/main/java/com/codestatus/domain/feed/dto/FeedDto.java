package com.codestatus.domain.feed.dto;

import com.codestatus.domain.hashTag.dto.HashTagResponseDto;
import com.codestatus.domain.status.entity.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
        private Status status;

        private Long likeCount;
        private Long commentCount;
        private List<HashTagResponseDto> feedHashTags;
    }
}
