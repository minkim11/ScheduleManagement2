package com.example.schedulemanagement2.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



// 전역 예외 처리
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bean Validation 예외 처리
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

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handlerServiceException(ServiceException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
