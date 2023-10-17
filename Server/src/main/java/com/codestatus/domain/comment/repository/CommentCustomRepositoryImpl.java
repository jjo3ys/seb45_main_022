package com.codestatus.domain.comment.repository;

import com.codestatus.domain.comment.entity.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.codestatus.domain.comment.entity.QComment.comment;
import static com.codestatus.domain.feed.entity.QFeed.feed;

@RequiredArgsConstructor
@Repository
public class CommentCustomRepositoryImpl implements CommentCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Comment> findCommentByCommentIdAndDeleted(long commentId, boolean deleted) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(comment)
                        .where(
                                eqCommentId(commentId),
                                eqDeleted(deleted)
                        ).fetchOne()
        );
    }

    @Override
    public List<Comment> findAllByUserUserIdAndDeletedIsFalse(long userId) {
        return jpaQueryFactory.selectFrom(comment)
                .where(
                        eqUserId(userId)
                ).fetch();
    }

    @Override
    public Page<Comment> findAllByFeed(long feedId, Pageable pageable) {
        List<Comment> content = getComments(feedId);
        JPAQuery<Long> countQuery = getCount(feedId);

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    private List<Comment> getComments(long feedId) {
        return jpaQueryFactory
                .selectFrom(comment)
                .join(comment)
                .on(eqFeedId(feedId))
                .fetch();
    }

    private JPAQuery<Long> getCount(long feedId) {
        return jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .join(comment.feed)
                .on(eqFeedId(feedId));
    }

    private BooleanExpression eqCommentId(long commentId) {
        return comment.commentId.eq(commentId);
    }
    private BooleanExpression eqDeleted(boolean deleted) {
        return comment.deleted.eq(deleted);
    }
    private BooleanExpression eqUserId(long userId) {
        return comment.user.userId.eq(userId);
    }
    private BooleanExpression eqFeedId(long feedId) {
        return feed.feedId.eq(feedId);
    }
}
