package com.example.schedulemanagement2.comment.controller;

import com.example.schedulemanagement2.comment.dto.CreateCommentRequest;
import com.example.schedulemanagement2.comment.dto.CreateCommentResponse;
import com.example.schedulemanagement2.comment.service.CommentService;
import com.example.schedulemanagement2.common.exception.UserNotLoginException;
import com.example.schedulemanagement2.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "login", required = false) SessionUser sessionUser,
            @RequestBody CreateCommentRequest request
    ) {
        if (sessionUser == null) {
            throw new UserNotLoginException("로그인이 필요합니다");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(scheduleId,sessionUser.getId(), request));
    }
}
