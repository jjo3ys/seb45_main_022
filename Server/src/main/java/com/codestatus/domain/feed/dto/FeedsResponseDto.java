package com.codestatus.domain.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FeedsResponseDto {
    private long feedId;

    private String nickname;

    private String profileImage;

    private int statId;

    private int level;

    private String body;

    private boolean isLike;

    private int likeCount;

    private int commentCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
