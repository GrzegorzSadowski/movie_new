package com.exadel.movie_new.dao;

import com.exadel.movie_new.model.Movie;
import org.springframework.stereotype.Component;

import java.util.List;

public interface MovieDao {

    List<Movie> readJsonData();
    void writeJsonData(List<Movie> movieData);

    Movie addMovie(Movie movie);

    List<Movie> getAllMovies();

    Movie getMovie(String movieId);

    Movie updateMovie(Movie movie);

    void deleteMovie(String movieId);

}
