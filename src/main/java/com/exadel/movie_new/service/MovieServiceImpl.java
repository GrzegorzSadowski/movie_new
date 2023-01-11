package com.exadel.movie_new.service;

import com.exadel.movie_new.dao.MovieDao;
import com.exadel.movie_new.exception.MovieAlreadyExists;
import com.exadel.movie_new.exception.MovieNotFoundException;
import com.exadel.movie_new.model.Movie;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDao movieDao;
    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }



    @Override
    public Movie  addMovie(Movie movie) {
        List<Movie> movieData = movieDao.read();
        boolean isPresent = false;
        if (!CollectionUtils.isEmpty(movieData)) {
            isPresent = movieData.parallelStream().anyMatch(mov -> mov.getId().equals(movie.getId()));
        } else { movieData = new ArrayList<>();}

        if (isPresent) {
            throw new MovieAlreadyExists("movie with such id " + movie.getId() + " already exists");
        } else {
            movieData.add(movie);
            movieDao.write(movieData);
        }
        return movie;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieData = movieDao.read();
        if (CollectionUtils.isEmpty(movieData)) {
            return Collections.emptyList();
        } else
        {return movieData;}
    }

    @Override
    public Movie getMovie(String movieId) {
        List<Movie> movieData = movieDao.read();
        Optional<Movie> foundMovie = searchMovie(movieData, movieId);
        if (foundMovie.isEmpty()) {
            throw new MovieNotFoundException("requested movie not found");
        }
        return foundMovie.get();
    }

    @Override
    public void updateMovie(Movie movie) {
        List<Movie> movieData = movieDao.read();
        if (searchMovie(movieData, movie.getId()).isEmpty()) {
            throw new MovieNotFoundException("cannot update because requested movie is not found");
        }
        movieData.removeIf(mov -> mov.getId().equals(movie.getId()));
        movieData.add(movie);
        movieDao.write(movieData);

    }

    @Override
    public void deleteMovie(String movieId) {
        List<Movie> movieData = movieDao.read();
        if (searchMovie(movieData, movieId).isEmpty()) {
            throw new MovieNotFoundException("the movie you are trying to delete does not exists");
        }
        movieData.removeIf(mov -> mov.getId().equals(movieId));
        movieDao.write(movieData);
    }

    public Optional<Movie> searchMovie (List<Movie> movieData, String movieId ){
        return movieData.parallelStream().filter(mov -> mov.getId().equals(movieId)).findAny();

    }
}
