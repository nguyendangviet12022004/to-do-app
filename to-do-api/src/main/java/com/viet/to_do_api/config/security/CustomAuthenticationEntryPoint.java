package com.viet.to_do_api.config.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.viet.to_do_api.constant.ExceptionCode;
import com.viet.to_do_api.dto.exception.ExceptionResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message("Unauthorized: " + authException.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(ExceptionCode.UNAUTHORIZED)
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }

}
