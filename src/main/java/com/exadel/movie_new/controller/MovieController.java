package com.exadel.movie_new.controller;

import com.exadel.movie_new.exception.GeneralException;
import com.exadel.movie_new.exception.IdNotValidException;
import com.exadel.movie_new.exception.MovieNotFoundException;
import com.exadel.movie_new.model.Movie;
import com.exadel.movie_new.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNumeric;


@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService =   movieService;
    }


    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        if (!isNumeric(movie.getId())) {
            throw new IdNotValidException("could not add a movie without valid id");
        }
        return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable String movieId) {

        if (!isNumeric(movieId)) {
            throw new IdNotValidException("you have to provide a valid Id");
        }
        if (movieService.getMovie(movieId) == null) {
            throw new MovieNotFoundException("requested movie not found");
        }

        try {
            return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.OK);
        } catch (Exception e) {
            throw new GeneralException("something went wrong" + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {


        if (movieService.getAllMovies().isEmpty()) {
            throw new MovieNotFoundException("no movies found");
        }

        try {
            return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
        } catch (Exception e) {
            throw new GeneralException("something went wrong" + e.getMessage());
        }
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
        if (!isNumeric(movie.getId())) {
            throw new IdNotValidException("you have to provide a valid Id");
        }
        if (movieService.getMovie(movie.getId()) == null) {
            throw new MovieNotFoundException("requested movie not found");
        }

        try {
            return new ResponseEntity<>(movieService.updateMovie(movie), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new GeneralException("something went wrong" + e.getMessage());
        }
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieId) {

        if (!isNumeric(movieId)) {
            throw new IdNotValidException("you have to provide a valid Id");
        }
        if (movieService.getMovie(movieId) == null) {
            throw new MovieNotFoundException("the movie you are trying to delete does not exists");
        }

        try {
            movieService.deleteMovie(movieId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new GeneralException("something went wrong" + e.getMessage());
        }
    }
}
