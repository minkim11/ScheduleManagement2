package com.example.schedulemanagement2.common.exception;

import org.springframework.http.HttpStatus;

// 일정 없을 경우 예외
public class ScheduleNotFoundException extends ServiceException{
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
