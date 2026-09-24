package com.example.lets_play.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException {

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    public ForbiddenException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}