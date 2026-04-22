package com.example.schedulemanagement2.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 로그인하지 않았을 경우 예외
@Getter
public class UserNotLoginException extends ServiceException{
    public UserNotLoginException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
