package com.example.schedulemanagement2.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 이메일 중복 시 예외
@Getter
public class EmailNotUniqueException extends ServiceException{
    public EmailNotUniqueException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
