package com.example.schedulemanagement2.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReadAllSchedulesResponse {
    private final Long id;
    private final String userName;
    private final String title;
    private final String description;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
