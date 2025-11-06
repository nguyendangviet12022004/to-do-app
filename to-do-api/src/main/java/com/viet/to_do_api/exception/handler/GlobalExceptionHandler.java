package com.viet.to_do_api.exception.handler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.viet.to_do_api.exception.task.ExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.viet.to_do_api.constant.ExceptionCode;
import com.viet.to_do_api.dto.exception.ExceptionResponse;
import com.viet.to_do_api.exception.auth.EmailExistsException;
import com.viet.to_do_api.exception.auth.TokenExpiredException;
import com.viet.to_do_api.exception.auth.TokenNotExistsException;

import lombok.NoArgsConstructor;

@RestControllerAdvice
@NoArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

        private ExceptionResponse getExceptionResponse(Exception ex, WebRequest request) {
                ExceptionResponse response = ExceptionResponse.builder()
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .code(ExceptionCode.INTERNAL_SERVER_ERROR)
                                .build();

                // email exists
                if (ex instanceof EmailExistsException) {
                        response.setStatus(HttpStatus.BAD_REQUEST);
                        response.setCode(ExceptionCode.EMAIL_EXISTS);
                }
                // method arg not valid
                else if (ex instanceof MethodArgumentNotValidException) {
                        response.setStatus(HttpStatus.BAD_REQUEST);
                        response.setCode(ExceptionCode.METHOD_ARGUMENT_NOT_VALID);
                }

                // token invalid
                else if (ex instanceof TokenNotExistsException) {
                        response.setStatus(HttpStatus.BAD_REQUEST);
                        response.setCode(ExceptionCode.TOKEN_NOT_EXISTS);
                }

                // token expired
                else if (ex instanceof TokenExpiredException) {
                        response.setStatus(HttpStatus.BAD_REQUEST);
                        response.setCode(ExceptionCode.TOKEN_EXPIRED);
                } else if (ex instanceof BadCredentialsException) {
                        response.setStatus(HttpStatus.BAD_REQUEST);
                        response.setCode(ExceptionCode.BAD_CREDENTIALS);
                } else if (ex instanceof DisabledException) {
                        response.setStatus(HttpStatus.BAD_REQUEST);
                        response.setCode(ExceptionCode.DISABLED_ACCOUNT);

                // exists title
                } else if(ex instanceof ExistsException){
                    response.setStatus(HttpStatus.BAD_REQUEST);
                    response.setCode(ExceptionCode.EXISTS_ERROR);
                }

                return response;

        }

        @Override
        @Nullable
        protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                        @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

                // get message string
                String message = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                                .collect(Collectors.joining("\n"));

                var response = getExceptionResponse(ex, request);
                response.setMessage(message);

                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(exception = Exception.class)
        public ResponseEntity<ExceptionResponse> CommonExceptionHanlder(Exception exception,
                        WebRequest request) {
                var response = getExceptionResponse(exception, request);

                return ResponseEntity.badRequest().body(response);
        }
}
