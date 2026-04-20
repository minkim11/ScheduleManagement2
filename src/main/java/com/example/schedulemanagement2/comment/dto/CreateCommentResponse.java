package com.example.schedulemanagement2.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {
    private final Long id;
    private final Long scheduleId;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
