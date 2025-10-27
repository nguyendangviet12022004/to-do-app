package com.viet.to_do_api.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TokenNotExistsException extends RuntimeException {
    public TokenNotExistsException(String message) {
        super(message);
    }
}
