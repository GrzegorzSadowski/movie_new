package com.exadel.movie_new.exception;

import org.springframework.stereotype.Component;


public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(String message) {
        super(message);
    }
}
