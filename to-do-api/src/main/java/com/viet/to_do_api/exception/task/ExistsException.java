package com.viet.to_do_api.exception.task;



public class ExistsException extends RuntimeException {
    public ExistsException(String message) {
        super(message);
    }
}
