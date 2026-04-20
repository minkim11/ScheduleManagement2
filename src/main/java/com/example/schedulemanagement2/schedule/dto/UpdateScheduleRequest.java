package com.example.schedulemanagement2.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateScheduleRequest {
    @NotBlank(message = "일정 제목을 입력해주세요")
    @Size(max = 20, message = "일정 제목을 20자 이내로 입력해주세요")
    private final String title;

    private final String description;
}
