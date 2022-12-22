package com.exadel.movie_new.controller;

import com.exadel.movie_new.model.Movie;
import com.exadel.movie_new.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MovieController {

    private MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @PostMapping("/addmovie")
    public Movie addMovie(@RequestBody Movie movie) {

        return movieService.addMovie(movie);
    }

    @GetMapping("/getmovie/{movieId}")
    public Movie getMovie(@PathVariable String movieId) {

        return movieService.getMovie(movieId);
    }

    @GetMapping("/getallmovies")
    public List<Movie> getAllMovies() {

        return movieService.getAllMovies();
    }

    @PutMapping("/updatemovie/{movieId}")
    public Movie updateMovie(@RequestBody Movie movie) {
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("deletemovie/{movieId}")
    public void deleteMovie(@PathVariable String movieId) {
        movieService.deleteMovie(movieId);
    }


}
