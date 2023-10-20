package com.codestatus.domain.feed.repository;

import com.codestatus.domain.feed.dto.FeedDto;
import com.codestatus.domain.feed.entity.Feed;

import com.codestatus.domain.hashTag.dto.HashTagResponseDto;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.codestatus.domain.category.entity.QCategory.category1;
import static com.codestatus.domain.comment.entity.QComment.comment;
import static com.codestatus.domain.feed.entity.QFeed.feed;
import static com.codestatus.domain.hashTag.entity.QFeedHashTag.feedHashTag;
import static com.codestatus.domain.hashTag.entity.QHashTag.hashTag;
import static com.codestatus.domain.like.entity.QLike.like;
import static com.codestatus.domain.status.entity.QStat.stat;
import static com.codestatus.domain.status.entity.QStatus.status;
import static com.codestatus.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Repository
public class FeedCustomRepositoryImpl implements FeedCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Long> findFeedsLikedByUserInList(long userId, List<Long> feeds) {
        return jpaQueryFactory
                .select(feed.feedId)
                .from(feed)
                .join(feed.likes, like)
                .join(like.user, user)
                .where(
                    inFeeds(feeds),
                    eqUserId(userId),
                    deletedFalse()
                ).fetch();
    }

    @Override
    public Optional<Feed> findByFeedIdWithUser(long feedId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(feed)
                        .join(feed.user, user).fetchJoin()
                        .where(eqFeedId(feedId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<FeedDto.FeedDetailDto> findByFeedIdWithUserStatusesAndCategoryStat(long feedId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(getFeedDetailDto())
                        .from(feed)
                        .innerJoin(feed.user, user)
                        .innerJoin(user.statuses, status).on(eqStatId())
                        .leftJoin(feed.likes, like).on(likeDeletedFalse())
                        .where(eqFeedId(feedId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Feed> findByFeedIdWithFeedCategoryStat(long feedId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(feed)
                        .join(feed.category, category1).fetchJoin()
                        .join(category1.stat, stat).fetchJoin()
                        .where(eqFeedId(feedId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Feed> findByFeedIdWithFeedCategoryStatAndUser(long feedId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(feed)
                        .join(feed.category, category1).fetchJoin()
                        .join(category1.stat, stat).fetchJoin()
                        .join(feed.user, user).fetchJoin()
                        .where(eqFeedId(feedId))
                        .fetchOne()
        );
    }

    @Override
    public Page<FeedDto.FeedListDto> findAllByUserAndDeleted(long categoryId, String user, Pageable pageable) {
        List<FeedDto.FeedListDto> content = getFeeds(categoryId, user, pageable);
        JPAQuery<Long> countQuery = getCount(categoryId, user);

        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchOne());
    }
    private List<FeedDto.FeedListDto> getFeeds(long categoryId, String nickname, Pageable pageable) {
        return jpaQueryFactory
                .select(getFeedListProjections())
                .from(feed)
                .innerJoin(feed.user, user)
                .innerJoin(user.statuses, status).on(eqStatId())
                .leftJoin(feed.likes, like).on(likeDeletedFalse())
                .leftJoin(feed.comments, comment).on(commentDeletedFalse())
                .where(
                        eqCategoryId(categoryId),
                        containsNickname(nickname),
                        deletedFalse()
                ).groupBy(feed.feedId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private JPAQuery<Long> getCount(long categoryId, String nickname) {
        return jpaQueryFactory
                .select(feed.count())
                .from(feed)
                .where(
                        eqCategoryId(categoryId),
                        containsNickname(nickname),
                        deletedFalse()
                );
    }

    @Override
    public Page<FeedDto.FeedListDto> findAllByCategoryIdAndHashTagId(long categoryId, long hashTagId, Pageable pageable) {
        List<FeedDto.FeedListDto> content = getFeeds(categoryId, hashTagId, pageable);
        JPAQuery<Long> countQuery = getCount(categoryId, hashTagId);

        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchOne());
    }
    private List<FeedDto.FeedListDto> getFeeds(long categoryId, long hashTagId, Pageable pageable) {
        return jpaQueryFactory
                .select(getFeedListProjections())
                .from(feed)
                .innerJoin(feed.user, user)
                .innerJoin(user.statuses, status).on(eqStatId())
                .leftJoin(feed.likes, like).on(likeDeletedFalse())
                .leftJoin(feed.comments, comment).on(commentDeletedFalse())
                .where(
                        eqCategoryId(categoryId),
                        eqHashTagId(hashTagId),
                        deletedFalse()
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private JPAQuery<Long> getCount(long categoryId, long hashTagId) {
        return jpaQueryFactory
                .select(feed.count())
                .from(feed)
                .join(feed.feedHashTags, feedHashTag)
                .join(feedHashTag.hashTag, hashTag)
                .where(
                        eqCategoryId(categoryId),
                        eqHashTagId(hashTagId),
                        deletedFalse()
                );
    }

    @Override
    public Page<FeedDto.FeedListDto> findAllByCategoryIdAndHashTagBodyContaining(long categoryId, String body, Pageable pageable) {
        List<FeedDto.FeedListDto> content = getFeedsWithHashTagBody(categoryId, body, pageable);
        JPAQuery<Long> countQuery = getCountWithHashTagBody(categoryId, body);

        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchOne());
    }
    private List<FeedDto.FeedListDto> getFeedsWithHashTagBody(long categoryId, String body, Pageable pageable) {
        return jpaQueryFactory
                .select(getFeedListProjections())
                .from(feed)
                .innerJoin(feed.user, user)
                .innerJoin(user.statuses, status).on(eqStatId())
                .leftJoin(feed.likes, like).on(likeDeletedFalse())
                .leftJoin(feed.comments, comment).on(commentDeletedFalse())
                .where(
                        eqCategoryId(categoryId),
                        containsHashTagBody(body),
                        deletedFalse()
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private JPAQuery<Long> getCountWithHashTagBody(long categoryId, String body) {
        return jpaQueryFactory
                .select(feed.count())
                .from(feed)
                .join(feed.feedHashTags, feedHashTag)
                .join(feedHashTag.hashTag, hashTag)
                .where(
                        eqCategoryId(categoryId),
                        containsHashTagBody(body),
                        deletedFalse()
                );
    }

    @Override
    public Page<FeedDto.FeedListDto> findAllByCategoryIdAndBodyContaining(long categoryId, String body, Pageable pageable) {
        List<FeedDto.FeedListDto> content = getFeedsWithBody(categoryId, body, pageable);
        JPAQuery<Long> countQuery = getCountWithBody(categoryId, body);

        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchOne());
    }
    private List<FeedDto.FeedListDto> getFeedsWithBody(long categoryId, String body, Pageable pageable) {
        return jpaQueryFactory
                .select(getFeedListProjections())
                .from(feed)
                .innerJoin(feed.user, user)
                .innerJoin(user.statuses, status).on(eqStatId())
                .leftJoin(feed.likes, like).on(likeDeletedFalse())
                .leftJoin(feed.comments, comment).on(commentDeletedFalse())
                .where(
                        eqCategoryId(categoryId),
                        containsBody(body),
                        deletedFalse()
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private JPAQuery<Long> getCountWithBody(long categoryId, String body) {
        return jpaQueryFactory
                .select(feed.count())
                .from(feed)
                .where(
                        eqCategoryId(categoryId),
                        containsBody(body),
                        deletedFalse()
                );
    }

    @Override
    public Page<FeedDto.FeedListDto> findAllByDeletedIsFalse(Pageable pageable) {
        List<FeedDto.FeedListDto> content = getFeeds(pageable);
        JPAQuery<Long> countQuery = getCount();

        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchOne());
    }
    private List<FeedDto.FeedListDto> getFeeds(Pageable pageable) {
        return jpaQueryFactory
                .select(getFeedListProjections())
                .from(feed)
                .innerJoin(feed.user, user)
                .innerJoin(user.statuses, status).on(eqStatId())
                .leftJoin(feed.likes, like).on(likeDeletedFalse())
                .leftJoin(feed.comments, comment).on(commentDeletedFalse())
                .where(
                        deletedFalse()
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private JPAQuery<Long> getCount() {
        return jpaQueryFactory
                .select(feed.count())
                .from(feed)
                .where(
                        deletedFalse()
                );
    }

    @Override
    public Page<FeedDto.FeedListDto> findAllByDeletedIsFalseAndCategoryId(long categoryId, Pageable pageable) {
        List<FeedDto.FeedListDto> content = getFeeds(categoryId, pageable);
        JPAQuery<Long> countQuery = getCount(categoryId);

        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchOne());
    }
    private List<FeedDto.FeedListDto> getFeeds(long categoryId, Pageable pageable) {
        return jpaQueryFactory
                .select(getFeedListProjections())
                .from(feed)
                .innerJoin(feed.user, user)
                .innerJoin(user.statuses, status).on(eqStatId())
                .leftJoin(feed.likes, like).on(likeDeletedFalse())
                .leftJoin(feed.comments, comment).on(commentDeletedFalse())
                .where(
                        eqCategoryId(categoryId),
                        deletedFalse()
                )
                .orderBy(feed.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(feed.feedId)
                .fetch();
    }
    private JPAQuery<Long> getCount(long categoryId) {
        return jpaQueryFactory
                .select(feed.count())
                .from(feed)
                .where(
                        eqCategoryId(categoryId),
                        deletedFalse()
                );
    }

    @Override
    public List<Feed> findAllByUserId(long userId) {
        return jpaQueryFactory
                .selectFrom(feed)
                .where(deletedFalse())
                .fetch();
    }

    @Override
    public Page<FeedDto.FeedListDto> findAllByUserId(long userId, Pageable pageable) {
        List<FeedDto.FeedListDto> content = getFeedsWithUserId(userId, pageable);
        JPAQuery<Long> countQuery = getCountWithUserId(userId);

        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchOne());
    }
    private List<FeedDto.FeedListDto> getFeedsWithUserId(long userId, Pageable pageable) {
        return jpaQueryFactory
                .select(getFeedListProjections())
                .from(feed)
                .innerJoin(feed.user, user)
                .innerJoin(user.statuses, status).on(eqStatId())
                .leftJoin(feed.likes, like).on(likeDeletedFalse())
                .leftJoin(feed.comments, comment).on(commentDeletedFalse())
                .where(
                        eqUserId(userId),
                        deletedFalse()
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private JPAQuery<Long> getCountWithUserId(long userId) {
        return jpaQueryFactory
                .select(feed.count())
                .from(feed)
                .where(
                        eqUserId(userId),
                        deletedFalse()
                );
    }

    @Override
    public Page<FeedDto.FeedListDto> findFeedsByCategoryAndCreatedAtAndSortLikes(Long categoryId, LocalDateTime oneWeekAgo, Pageable pageable) {
        List<FeedDto.FeedListDto> content = getFeeds(categoryId, oneWeekAgo, pageable);
        JPAQuery<Long> countQuery = getCount(categoryId, oneWeekAgo);

        return PageableExecutionUtils.getPage(content, pageable, () ->countQuery.fetchOne());
    }
    private List<FeedDto.FeedListDto> getFeeds(Long categoryId, LocalDateTime oneWeekAgo, Pageable pageable) {
        return jpaQueryFactory
                .select(getFeedListProjections())
                .from(feed)
                .innerJoin(feed.user, user)
                .innerJoin(user.statuses, status).on(eqStatId())
                .leftJoin(feed.likes, like).on(likeDeletedFalse())
                .leftJoin(feed.comments, comment).on(commentDeletedFalse())
                .where(
                        eqCategoryId(categoryId),
                        oneWeekAgo(oneWeekAgo),
                        deletedFalse()
                ).groupBy(feed.feedId)
                .orderBy(like.countDistinct().desc(), feed.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private JPAQuery<Long> getCount(Long categoryId, LocalDateTime oneWeekAgo) {
        return jpaQueryFactory
                .select(feed.count())
                .from(feed)
                .where(
                        eqCategoryId(categoryId),
                        oneWeekAgo(oneWeekAgo),
                        deletedFalse(),
                        likeDeletedFalse()
                );
    }

    // projections constructor
    private ConstructorExpression<FeedDto.FeedListDto> getFeedListProjections() {
        return Projections.constructor(FeedDto.FeedListDto.class,
                        feed.feedId,
                        feed.body,
                        feed.category.stat.statId,
                        feed.createdAt,
                        feed.modifiedAt,

                        feed.user.userId,
                        feed.user.nickname,
                        feed.user.profileImage,
                        status.statLevel,

                        like.countDistinct(),
                        comment.countDistinct()
                );
    }

    private ConstructorExpression<FeedDto.FeedDetailDto> getFeedDetailDto() {
        return Projections.constructor(FeedDto.FeedDetailDto.class,
                feed.feedId,
                feed.data,
                user.userId,
                user.nickname,
                user.profileImage,
                feed.category.stat.statId,
                status.statLevel,
                like.countDistinct(),
                feed.createdAt,
                feed.modifiedAt,
                feed.deleted
            );
    }

    // BooleanExpression
    private BooleanExpression inFeeds(List<Long> feedList) {
        return !feedList.isEmpty() ? feed.feedId.in(feedList) : null;
    }

    private BooleanExpression deletedFalse() {
        return feed.deleted.isFalse();
    }
    private BooleanExpression likeDeletedFalse() {
        return like.deleted.isFalse();
    }
    private BooleanExpression commentDeletedFalse() {
        return comment.deleted.isFalse();
    }

    private BooleanExpression eqFeedId(long feedId) {
        return feed.feedId.eq(feedId);
    }
    private BooleanExpression eqUserId(long userId) {
        return user.userId.eq(userId);
    }
    private BooleanExpression eqCategoryId(long categoryId) {
        return feed.category.categoryId.eq(categoryId);
    }
    private BooleanExpression eqHashTagId(long hashTagId) {
        return hashTag.hashTagId.eq(hashTagId);
    }
    private BooleanExpression eqStatId() {
        return status.stat.statId.eq(feed.category.stat.statId);
    }

    private BooleanExpression containsBody(String body) {
        return StringUtils.hasText(body) ? feed.body.contains(body) : null;
    }
    private BooleanExpression containsNickname(String nickname) {
        return StringUtils.hasText(nickname) ? feed.user.nickname.contains(nickname) : null;
    }
    private BooleanExpression containsHashTagBody(String body) {
        return StringUtils.hasText(body) ? hashTag.body.contains(body) : null;
    }

    private BooleanExpression oneWeekAgo(LocalDateTime oneWeekAgo) {
        return oneWeekAgo == null ? null : feed.createdAt.after(oneWeekAgo);
    }
}
