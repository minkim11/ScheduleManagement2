package com.example.schedulemanagement2.user.dto;

import lombok.Getter;

@Getter
public class SessionUser {
    private final Long id;
    private final String email;
    private final String name;

    public SessionUser(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
