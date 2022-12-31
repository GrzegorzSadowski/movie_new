package com.exadel.movie_new.servicetests;

import com.exadel.movie_new.dao.MovieDaoImpl;
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
import static org.mockito.Mockito.*;


@SpringBootTest
public class MovieServiceTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieDaoImpl movieDao;

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
        when(movieDao.addMovie(any(Movie.class))).thenReturn(newMovie);
        Movie savedMovie = movieService.addMovie(newMovie);
        assertNotNull(savedMovie);
        assertFalse(savedMovie.getId().isEmpty());
        assertEquals(newMovie.getTitle(), savedMovie.getTitle());
        assertEquals(newMovie.getId(), savedMovie.getId());
        assertEquals(newMovie.getGenres().get(0), savedMovie.getGenres().get(0));

    }
    @Test
    @DisplayName("Should fetch all movies")
    void shouldGetAllMovies(){

        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        list.add(newMovie2);

        when(movieDao.getAllMovies()).thenReturn(list);
        List<Movie> movies = movieService.getAllMovies();
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals(2, movies.size());
    }

    @Test
    @DisplayName("Should fetch movie by Id")
    void shouldGetMovieById(){
        when(movieDao.getMovie(anyString())).thenReturn(newMovie);
        Movie existingMovie = movieService.getMovie(newMovie.getId());
        assertNotNull(existingMovie);
        assertNotEquals(null, existingMovie.getId());
        assertEquals(newMovie.getId(), existingMovie.getId());
    }

    @Test
    @DisplayName("Should throw exception")
    void shouldGetMovieByIdException(){
        when(movieDao.getMovie("200")).thenThrow(MovieNotFoundException.class);
        assertThrows(MovieNotFoundException.class, ()->movieService.getMovie("200"));
    }

    @Test
    @DisplayName("Should update movie")
    void shouldUpdateMovie(){
        when(movieDao.updateMovie(any())).thenReturn(newMovie);
        newMovie.setTitle("Mad Max");
        Movie existingMovie= movieService.updateMovie(newMovie);
        assertNotNull(existingMovie);
        assertEquals("Mad Max", existingMovie.getTitle());
    }
    @Test
    @DisplayName("Should delete movie")
    void shouldDeleteMovie(){
        String movieId= "100";
        when(movieDao.getMovie("100")).thenReturn(newMovie);
        doNothing().when(movieDao).deleteMovie(anyString());
        movieService.deleteMovie(movieId);
        verify(movieDao, times(1)).deleteMovie(newMovie.getId());
    }
}
