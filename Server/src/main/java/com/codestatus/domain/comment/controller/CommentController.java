package com.codestatus.domain.comment.controller;

import com.codestatus.domain.feed.mapper.FeedMapper;
import com.codestatus.domain.user.mapper.UserMapper;
import com.codestatus.global.auth.dto.PrincipalDto;
import com.codestatus.domain.comment.dto.CommentPostDto;
import com.codestatus.domain.comment.dto.CommnetPatchDto;
import com.codestatus.domain.comment.entity.Comment;
import com.codestatus.domain.comment.mapper.CommentMapper;
import com.codestatus.domain.comment.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentServiceImpl commentServiceImpl;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final FeedMapper feedMapper;

    @PostMapping("/{feedId}")
    public ResponseEntity postComment(@PathVariable("feedId") long feedId,
                                      @Valid @RequestBody CommentPostDto requestBody,
                                      @AuthenticationPrincipal PrincipalDto principal) {
        Comment comment = commentMapper.commentPostDtoToComment(requestBody);
        comment.setFeed(feedMapper.feedIdToFeed(feedId));
        comment.setUser(userMapper.userIdToUser(principal.getId()));

        commentServiceImpl.createEntity(comment);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity patchComment(@PathVariable("commentId") long commentId,
                                       @Valid @RequestBody CommnetPatchDto requestBody,
                                       @AuthenticationPrincipal PrincipalDto principal){
        Comment comment = commentMapper.commentPatchDtoToComment(requestBody);
        comment.setCommentId(commentId);

        commentServiceImpl.updateEntity(comment, principal.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") long commentId,
                                       @AuthenticationPrincipal PrincipalDto principal){
        commentServiceImpl.deleteEntity(commentId, principal.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
