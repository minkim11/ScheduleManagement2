package com.example.schedulemanagement2.schedule.dto;

import com.example.schedulemanagement2.comment.dto.ReadAllCommentsResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReadOneScheduleResponse {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<ReadAllCommentsResponse> comments;
}
