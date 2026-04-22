package com.example.schedulemanagement2.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 커스텀 예외 전역 처리를 위한 공통 부모 예외
@Getter
public class ServiceException extends RuntimeException{
    private final HttpStatus httpStatus;


    public ServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
