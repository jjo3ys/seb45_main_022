package com.codestatus.domain.comment.command;

import com.codestatus.domain.comment.entity.Comment;
import com.codestatus.domain.comment.repository.CommentRepository;
import com.codestatus.global.exception.BusinessLogicException;
import com.codestatus.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Component
public class CommentCommand {
    private final CommentRepository commentRepository;

    // comment가 존재하는지 검사
    @Transactional(readOnly = true)
    public Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findCommentByCommentIdAndDeleted(commentId, false);
        return optionalComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    public void deleteCommentAll(long userId){
        List<Comment> commentList = commentRepository.findAllByUserUserIdAndDeletedIsFalse(userId);
        deleteCommentAll(commentList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCommentAll(List<Comment> commentList){
        commentList.forEach(comment -> comment.setDeleted(true));

        commentRepository.saveAll(commentList);
    }
}
