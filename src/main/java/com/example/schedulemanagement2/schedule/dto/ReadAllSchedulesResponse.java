package com.example.schedulemanagement2.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadAllSchedulesResponse {
    private final Long id;
    private final Long userId;
    private final String title;
}
