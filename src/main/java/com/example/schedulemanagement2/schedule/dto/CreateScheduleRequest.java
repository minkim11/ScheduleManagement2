package com.example.schedulemanagement2.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateScheduleRequest {
    private final String title;
    private final String description;
}
