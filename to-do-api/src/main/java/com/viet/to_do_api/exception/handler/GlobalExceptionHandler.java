package com.viet.to_do_api.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.viet.to_do_api.dto.exception.ExceptionResponse;
import com.viet.to_do_api.exception.auth.EmailExistsException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = EmailExistsException.class)
    public ResponseEntity<ExceptionResponse> emailExistExceptionHanlder(EmailExistsException exception,
            HttpServletRequest request) {
        var response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
