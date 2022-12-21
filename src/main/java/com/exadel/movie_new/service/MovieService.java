package com.exadel.movie_new.service;

import com.exadel.movie_new.model.Movie;

public interface MovieService {

    Movie addMovie(Movie movie);

    Movie getMovieDetails(String movieId);

    Movie updateMovie(Movie movie);

    void deleteMovie(String movieId);
}
