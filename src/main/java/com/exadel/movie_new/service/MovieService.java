package com.exadel.movie_new.service;

import com.exadel.movie_new.model.Movie;

import java.util.List;

public interface MovieService {

    Movie addMovie(Movie movie);

    List<Movie> getAllMovies();

    Movie getMovie(String movieId);

    void updateMovie(Movie movie);

    void deleteMovie(String movieId);
}
