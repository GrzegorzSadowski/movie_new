package com.exadel.movie_new.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MovieExceptionHandler {
    @ExceptionHandler(value = {MovieNotFoundException.class})
    public ResponseEntity<Object> handleMovieNotFoundException
            (MovieNotFoundException e) {
        ErrorInfo errorInfo = new ErrorInfo("Sorry " + e.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IdNotValidException.class})
    public ResponseEntity<Object> handleIdNotValidException
            (IdNotValidException e) {
        ErrorInfo errorInfo = new ErrorInfo("Sorry " + e.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MovieAlreadyExists.class})
    public ResponseEntity<Object> handleGeneralException
            (MovieAlreadyExists e) {
        ErrorInfo errorInfo = new ErrorInfo("Sorry " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {FilesException.class})
    public ResponseEntity<Object> handleGeneralException
            (FilesException e) {
        ErrorInfo errorInfo = new ErrorInfo("Sorry " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
