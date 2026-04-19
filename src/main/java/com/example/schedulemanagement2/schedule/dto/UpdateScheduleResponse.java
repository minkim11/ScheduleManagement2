package com.example.schedulemanagement2.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateScheduleResponse {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String description;
    private final LocalDateTime modifiedAt;
}
