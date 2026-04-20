package com.example.schedulemanagement2.common.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ServiceException{
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
