package com.example.schedulemanagement2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank
    @Size(max = 10)
    private final String name;

    @NotBlank
    @Email
    @Size(max = 50)
    private final String email;

    @NotBlank
    @Size(min = 8, max = 16)
    private final String password;

    public CreateUserRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
