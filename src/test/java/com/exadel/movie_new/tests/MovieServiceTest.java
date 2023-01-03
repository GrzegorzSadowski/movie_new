package com.exadel.movie_new.tests;

import com.exadel.movie_new.dao.MovieDaoImpl;
import com.exadel.movie_new.exception.MovieAlreadyExists;
import com.exadel.movie_new.exception.MovieNotFoundException;
import com.exadel.movie_new.model.Movie;
import com.exadel.movie_new.service.MovieServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
public class MovieServiceTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieDaoImpl movieDaoImplMock;

    private final Movie newMovie;
    private final Movie newMovie2;

    {
        newMovie = new Movie();
        newMovie.setId("100");
        newMovie.setTitle("Kill Bill");
        newMovie.setGenres(List.of("Comedy", "Romance"));

        newMovie2 = new Movie();
        newMovie2.setId("101");
        newMovie2.setTitle("Anastazja");
        newMovie2.setGenres(List.of("Comedy", "Romance"));
    }

    @Test
    @DisplayName("Should save the movie to a file")
    void shouldAddNewMovie() {
        doNothing().when(movieDaoImplMock).write(any());
        Movie savedMovie = movieService.addMovie(newMovie);
        assertNotNull(savedMovie);
        assertFalse(savedMovie.getId().isEmpty());
        assertEquals(newMovie.getTitle(), savedMovie.getTitle());
        assertEquals(newMovie.getId(), savedMovie.getId());
        assertEquals(newMovie.getGenres().get(0), savedMovie.getGenres().get(0));
    }

    @Test
    @DisplayName("Should throw exception that movie already exists")
    void shouldThrowExceptionMovieAlreadyExists() {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        list.add(newMovie2);
        when(movieDaoImplMock.read()).thenReturn(list);
        assertThrows(MovieAlreadyExists.class, () -> movieService.addMovie(newMovie));
    }

    @Test
    @DisplayName("Should fetch all movies")
    void shouldGetAllMovies() {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        list.add(newMovie2);
        when(movieDaoImplMock.read()).thenReturn(list);
        List<Movie> movies = movieService.getAllMovies();
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals(2, movies.size());
    }

    @Test
    @DisplayName("Should return an empty list")
    void shouldGetEmptyList() {
        List<Movie> list = new ArrayList<>();
        when(movieDaoImplMock.read()).thenReturn(list);
        List<Movie> movies = movieService.getAllMovies();
        assertNotNull(movies);
        assertTrue(movies.isEmpty());
        assertEquals(0,movies.size());
    }

    @Test
    @DisplayName("Should fetch movie by Id")
    void shouldGetMovieById() {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);

        when(movieDaoImplMock.read()).thenReturn(list);
        Movie existingMovie = movieService.getMovie(newMovie.getId());
        assertNotNull(existingMovie);
        assertNotEquals(null, existingMovie.getId());
        assertEquals(newMovie.getId(), existingMovie.getId());
    }

    @Test
    @DisplayName("Should throw MovieNotFoundException while getMovie")
    void shouldGetMovieByIdException() {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        when(movieDaoImplMock.read()).thenReturn(list);
        assertThrows(MovieNotFoundException.class, () -> movieService.getMovie("200"));
    }

    @Test
    @DisplayName("Should update movie")
    void shouldUpdateMovie() {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        when(movieDaoImplMock.read()).thenReturn(list);
        doNothing().when(movieDaoImplMock).write(any());
        newMovie.setTitle("Mad Max");
        Movie existingMovie = movieService.updateMovie(newMovie);
        assertNotNull(existingMovie);
        assertEquals("Mad Max", existingMovie.getTitle());
    }

    @Test
    @DisplayName("Should thrown MovieNotFoundException while updateMovie")
    void shouldUpdateMovieException() {
        List<Movie> list = new ArrayList<>();
        when(movieDaoImplMock.read()).thenReturn(list);
        assertThrows(MovieNotFoundException.class, () -> movieService.updateMovie(newMovie));
    }

    @Test
    @DisplayName("Should delete movie")
    void shouldDeleteMovie() {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        list.add(newMovie2);
        String movieId = "100";
        when(movieDaoImplMock.read()).thenReturn(list);
        doNothing().when(movieDaoImplMock).write(any());
        movieService.deleteMovie(movieId);
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Should throw MovieNotFoundException while deleteMovie")
    void shouldDeleteMovieException() {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        list.add(newMovie2);
        when(movieDaoImplMock.read()).thenReturn(list);
        assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie("200"));
    }
}
