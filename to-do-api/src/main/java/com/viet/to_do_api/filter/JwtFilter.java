package com.viet.to_do_api.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.header.XXssProtectionServerHttpHeadersWriter.HeaderValue;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.viet.to_do_api.constant.ExceptionCode;
import com.viet.to_do_api.dto.exception.ExceptionResponse;
import com.viet.to_do_api.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private void writeErrorResponse(HttpServletResponse response, HttpServletRequest request,
            String message, ExceptionCode code, HttpStatus status) throws IOException {

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(message)
                .status(status)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .code(code)
                .build();

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }

    private String extractAccessTokenFromHeader(@NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        String accessToken = authHeader != null && authHeader.startsWith("Bearer")
                ? authHeader.split(" ")[1].trim()
                : null;
        return accessToken;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = extractAccessTokenFromHeader(request);

        if (accessToken != null) {
            try {
                Authentication authentication = jwtService.extractAuthentication(accessToken);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException ex) {
                writeErrorResponse(response, request, "JWT token expired", ExceptionCode.TOKEN_EXPIRED,
                        HttpStatus.UNAUTHORIZED);
                return;
            } catch (JwtException ex) {
                writeErrorResponse(response, request, "Invalid JWT token", ExceptionCode.TOKEN_INVALID,
                        HttpStatus.UNAUTHORIZED);
                return;
            } catch (Exception ex) {
                writeErrorResponse(response, request, "Authentication error", ExceptionCode.AUTHENTICATION_ERROR,
                        HttpStatus.UNAUTHORIZED);
                return;
            }

        } else

        {
            doFilter(request, response, filterChain);
        }

    }

    @Override
    protected boolean shouldNotFilter(@Nonnull HttpServletRequest request) throws ServletException {
        return request.getServletPath().contains("/auth");
    }

}
