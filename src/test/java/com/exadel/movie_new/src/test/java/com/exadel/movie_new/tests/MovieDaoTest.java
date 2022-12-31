package com.exadel.movie_new.tests;

import com.exadel.movie_new.dao.MovieDao;
import com.exadel.movie_new.model.Movie;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieDaoTest {

    @Autowired
    private MovieDao movieDao;

    private final Movie newMovie;

    private static int initialSize;

    {
        newMovie = new Movie();
        newMovie.setId("100");
        newMovie.setTitle("Kill Bill");
        newMovie.setGenres(List.of("Comedy", "Romance"));

    }

    @Test
    @DisplayName("It should return not empty list")
    void notEmpty() {
        List<Movie> moviesList = movieDao.getAllMovies();
        assertFalse(moviesList.isEmpty());
    }

    @Test
    @Order(1)
    @DisplayName("It should add the new movie")
    void addNewMovie() {
        initialSize = movieDao.getAllMovies().size();
        movieDao.addMovie(newMovie);
        assertEquals(initialSize+1, movieDao.getAllMovies().size());
    }

    @Test
    @Order(2)
    @DisplayName("It should return the movie by its id")
    void getMovieById() {
        Movie searchedMovie = movieDao.getMovie(newMovie.getId());
        assertNotNull(searchedMovie);
        assertEquals("Kill Bill", searchedMovie.getTitle());
    }

    @Test
    @Order(3)
    @DisplayName("It should update the movie title with Mad Max")
    void updateMovie() {
        Movie existingMovie = movieDao.getMovie(newMovie.getId());
        existingMovie.setTitle("Mad Max");
        Movie updatedMovie = movieDao.updateMovie(existingMovie);
        assertEquals("Mad Max", updatedMovie.getTitle());
        assertEquals("100", updatedMovie.getId());
    }

    @Test
    @Order(4)
    @DisplayName("It should delete the existing movie")
    void deleteMovie() {
        movieDao.deleteMovie(newMovie.getId());
        List<Movie> list = movieDao.getAllMovies();
        assertEquals(initialSize, list.size());
    }
}

