package com.codestatus.domain.feed.repository;

import com.codestatus.domain.feed.dto.FeedDto;
import com.codestatus.domain.feed.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FeedCustomRepository {
    // feed 리스트에서 user가 좋아요 한 feed의 id 만
    List<Long> findFeedsLikedByUserInList(@Param("userId") long userId, @Param("feeds") List<Long> feeds);

    // user data와 함께 조회
    Optional<Feed> findByFeedIdWithUser(long feedId);

    // user data, user의 statuses, category의 stat과 함께 조회
    Optional<Feed> findByFeedIdWithUserStatusesAndCategoryStat(long feedId);

    Optional<Feed> findByFeedIdWithFeedCategoryStat(long feedId);

    Optional<Feed> findByFeedIdWithFeedCategoryStatAndUser(long feedId);

    Page<FeedDto.FeedListDto> findAllByUserAndDeleted(@Param("categoryId")long categoryId, @Param("user") String user, Pageable pageable);

    Page<FeedDto.FeedListDto> findAllByCategoryIdAndHashTagId(long categoryId, long hashTagId, Pageable pageable);

    Page<FeedDto.FeedListDto> findAllByCategoryIdAndHashTagBodyContaining(long categoryId, String body, Pageable pageable);

    Page<FeedDto.FeedListDto> findAllByCategoryIdAndBodyContaining(long categoryId, String body, Pageable pageable);

    Page<FeedDto.FeedListDto> findAllByDeletedIsFalse(Pageable pageable);

    Page<FeedDto.FeedListDto> findAllByDeletedIsFalseAndCategoryId(long categoryId, Pageable pageable);

    List<Feed> findAllByUserId(long userId);

    Page<FeedDto.FeedListDto> findAllByUserId(long userId, Pageable pageable);

    // 일주일 안에 작성된 피드를 likes 의 사이즈 순으로 정렬해서 조회
    Page<FeedDto.FeedListDto> findFeedsByCategoryAndCreatedAtAndSortLikes(
            Long categoryId,
            LocalDateTime oneWeekAgo,
            Pageable pageable
    );
}
