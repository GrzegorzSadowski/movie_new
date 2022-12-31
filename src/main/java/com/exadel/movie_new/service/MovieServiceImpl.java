package com.exadel.movie_new.service;

import com.exadel.movie_new.dao.MovieDao;
import com.exadel.movie_new.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDao movieDao;
    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }


    @Override
    public Movie addMovie(Movie movie) {
        return movieDao.addMovie(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    @Override
    public Movie getMovie(String movieId) {
        return movieDao.getMovie(movieId);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        return movieDao.updateMovie(movie);
    }

    @Override
    public void deleteMovie(String movieId) {
        movieDao.deleteMovie(movieId);
    }
}
