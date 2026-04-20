package com.example.schedulemanagement2.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 10, message = "이름은 10자 이내로 입력해주세요")
    private final String name;

    public UpdateUserRequest(String name) {
        this.name = name;
    }
}
