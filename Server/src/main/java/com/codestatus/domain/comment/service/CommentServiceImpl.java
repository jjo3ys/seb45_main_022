package com.codestatus.domain.comment.service;

import com.codestatus.domain.comment.command.CommentCommand;
import com.codestatus.domain.comment.entity.Comment;
import com.codestatus.domain.comment.repository.CommentRepository;
import com.codestatus.domain.feed.command.FeedCommand;
import com.codestatus.global.exception.BusinessLogicException;
import com.codestatus.global.exception.ExceptionCode;
import com.codestatus.domain.utils.user.CheckUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentCommand commentCommand;
    private final FeedCommand feedCommand;

    @Override
    public void createEntity(Comment comment) {
        feedCommand.findVerifiedFeed(comment.getFeed().getFeedId());
        commentRepository.save(comment);
    }

    @Override
    public Page<Comment> getEntitys(long feedId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findAllByFeed(feedId, pageable);
    }

    // comment가 존재하는지, 요청한 유저와 리소스의 주인이 일치하는지 검사하고,
    // body값의 null 판별을 통해 수정
    @Override
    public void updateEntity(Comment comment, long userId) {
        Comment findComment = commentCommand.findVerifiedComment(comment.getCommentId());
        CheckUser.isCreator(findComment.getUser().getUserId(), userId);
        Optional.ofNullable(comment.getBody()).ifPresent(findComment::setBody);

        commentRepository.save(findComment);
    }

    // comment가 존재하는지, 요청한 유저와 리소스의 주인이 일치하는지 검사하고,
    // db에서 완전 삭제가 아닌 deleted=true 로 수정
    @Override
    public void deleteEntity(long commentId, long userId) {
        Comment findComment = commentCommand.findVerifiedComment(commentId);
        CheckUser.isCreator(findComment.getUser().getUserId(), userId);
        findComment.setDeleted(true);

        commentRepository.save(findComment);
    }
}
