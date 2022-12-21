package com.exadel.movie_new.controller;

import com.exadel.movie_new.model.Movie;
import com.exadel.movie_new.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping("/movie")
    public Movie addMovie(@RequestBody Movie movie) {

        return movieService.addMovie(movie);
    }

    @GetMapping("/movie/{movieId}")
    public Movie getMovieDetails(@PathVariable String movieId) {

        return movieService.getMovieDetails(movieId);
    }

    @PutMapping("/movie/{movieId}")
    public Movie updateMovie(@RequestBody Movie movie) {
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("movie/{movieId}")
    public void deleteMovie(@PathVariable String movieId) {
        movieService.deleteMovie(movieId);
    }


}
