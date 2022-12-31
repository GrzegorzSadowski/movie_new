package com.exadel.movie_new.exception;

public class MovieAlreadyExists extends RuntimeException {
    public MovieAlreadyExists(String message) {
        super(message);
    }
}
