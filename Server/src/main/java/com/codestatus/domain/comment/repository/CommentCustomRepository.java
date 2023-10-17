package com.codestatus.domain.comment.repository;

import com.codestatus.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {
    Optional<Comment> findCommentByCommentIdAndDeleted(long commentId, boolean deleted);
    List<Comment> findAllByUserUserIdAndDeletedIsFalse(long userId);
    Page<Comment> findAllByFeed(long feedId, Pageable pageable);
}
