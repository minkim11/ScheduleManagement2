package com.example.schedulemanagement2.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateScheduleRequest {
    @NotBlank
    @Size(max = 20)
    private final String title;

    private final String description;
}
