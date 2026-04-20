package com.example.schedulemanagement2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handlerNotValidException(MethodArgumentNotValidException e) {
        String exceptionMessage = e.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(exception -> exception.getDefaultMessage())
                .orElse("잘못된 입력값입니다.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handlerUniqueEmailException(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 이메일");
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handlerServiceException(ServiceException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
