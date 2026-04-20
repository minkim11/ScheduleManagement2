package com.example.schedulemanagement2.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotLoginException extends ServiceException{
    public UserNotLoginException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
