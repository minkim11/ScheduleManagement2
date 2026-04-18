package com.example.schedulemanagement2.user.dto;

import lombok.Getter;

@Getter
public class SessionUser {
    public final Long id;
    public final String email;

    public SessionUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
