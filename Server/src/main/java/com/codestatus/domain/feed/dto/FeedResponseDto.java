package com.codestatus.domain.feed.dto;

import com.codestatus.domain.hashTag.dto.HashTagResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FeedResponseDto {

    private long feedId;

    private String nickname;

    private String profileImage;

    private int statId;

    private int level;

    private String data;

    private List<HashTagResponseDto> feedHashTags;

    private boolean isLike;

    private int likeCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
