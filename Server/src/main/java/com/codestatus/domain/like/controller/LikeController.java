package com.codestatus.domain.like.controller;

import com.codestatus.domain.like.service.LikeService;
import com.codestatus.auth.dto.PrincipalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/feed/like")
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{feedId}")
    public ResponseEntity likeFeed(@PathVariable("feedId") long feedId,
                                            @AuthenticationPrincipal PrincipalDto principal) {
        likeService.likeFeed(feedId, principal.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{feedId}")
    public ResponseEntity dislikeFeed(@PathVariable("feedId") long feedId,
                                            @AuthenticationPrincipal PrincipalDto principal) {
        likeService.disLikeFeed(feedId, principal.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
