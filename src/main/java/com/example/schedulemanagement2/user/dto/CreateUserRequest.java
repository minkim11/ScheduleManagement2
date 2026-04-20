package com.example.schedulemanagement2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 10, message = "이름은 10자 이내로 입력해주세요")
    private final String name;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    @Size(max = 50, message = "이메일은 50자 이내로 입력해주세요")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 16, message = "비밀번호는 8자 ~ 16자 이내로 입력해주세요")
    private final String password;

    public CreateUserRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
