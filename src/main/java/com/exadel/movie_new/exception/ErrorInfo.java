package com.exadel.movie_new.exception;

import org.springframework.http.HttpStatus;

public class ErrorInfo {

    private final String message;

    private HttpStatus httpStatus;

    public ErrorInfo(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
    public ErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
