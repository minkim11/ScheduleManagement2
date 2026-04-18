package com.example.schedulemanagement2.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @NotBlank
    @Size(max = 10)
    private final String name;

    public UpdateUserRequest(String name) {
        this.name = name;
    }
}
