package com.example.schedulemanagement2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank
    @Email
    @Size(max = 50)
    private final String email;

    @NotBlank
    @Size(min = 8, max = 16)
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
