package com.example.schedulemanagement2.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadAllCommentsResponse {
    private final Long commentId;
    private final Long scheduleId;
    private final String comment;
}
