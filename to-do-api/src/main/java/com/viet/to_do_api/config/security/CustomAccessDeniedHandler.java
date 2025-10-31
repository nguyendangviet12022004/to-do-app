package com.viet.to_do_api.config.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.viet.to_do_api.constant.ExceptionCode;
import com.viet.to_do_api.dto.exception.ExceptionResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message("Forbidden: " + accessDeniedException.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(ExceptionCode.FORBIDDEN)
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }

}
