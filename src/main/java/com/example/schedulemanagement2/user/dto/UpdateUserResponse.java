package com.example.schedulemanagement2.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserResponse {
    private final Long id;
    private final String name;
    private final LocalDateTime modifiedAt;

    public UpdateUserResponse(Long id, String name, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.modifiedAt = modifiedAt;
    }
}
