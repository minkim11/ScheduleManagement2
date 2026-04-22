package com.example.schedulemanagement2.comment.controller;

import com.example.schedulemanagement2.comment.dto.CreateCommentRequest;
import com.example.schedulemanagement2.comment.dto.CreateCommentResponse;
import com.example.schedulemanagement2.comment.dto.ReadAllCommentsResponse;
import com.example.schedulemanagement2.comment.service.CommentService;
import com.example.schedulemanagement2.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 일정에 댓글 생성
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "login", required = false) SessionUser sessionUser,
            @RequestBody CreateCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(scheduleId,sessionUser, request));
    }

    // 댓글 전체 조회
    @GetMapping("/schedules/comments")
    public ResponseEntity<List<ReadAllCommentsResponse>> readComment(
            @SessionAttribute(name = "login", required = false) SessionUser sessionUser
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.readAllComments(sessionUser));
    }
}
