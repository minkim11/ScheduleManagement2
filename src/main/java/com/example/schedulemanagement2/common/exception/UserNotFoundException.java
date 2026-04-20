package com.example.schedulemanagement2.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends ServiceException{
    public UserNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
