package com.example.schedulemanagement2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    @Size(max = 50, message = "이메일은 50자 이내로 입력해주세요")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 16, message = "비밀번호는 8자 ~ 16자 이내로 입력해주세요")
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
