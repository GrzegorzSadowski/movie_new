package com.exadel.movie_new.tests;

import com.exadel.movie_new.controller.MovieController;
import com.exadel.movie_new.model.Movie;
import com.exadel.movie_new.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@RunWith(SpringRunner.class)
public class MovieControllerTest {
    @MockBean
    MovieService movieService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

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
        newMovie2.setGenres(List.of("Action", "Romance"));
    }

    @Test
    public void shouldCreateNewMovie() throws Exception {

        when(movieService.addMovie(any(Movie.class))).thenReturn(newMovie);

        this.mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMovie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newMovie.getId()))
                .andExpect(jsonPath("$.title").value(newMovie.getTitle()))
                .andExpect(jsonPath("$.genres[0]").value(newMovie.getGenres().get(0)))
                .andExpect(jsonPath("$.genres[1]").value(newMovie.getGenres().get(1)));

    }

    @Test
    public void shouldGetAllMovies() throws Exception {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        list.add(newMovie2);

        when(movieService.getAllMovies()).thenReturn(list);
        this.mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(list.size()));
    }

    @Test
    public void shouldGetOneMovieById() throws Exception {
        List<Movie> list = new ArrayList<>();
        list.add(newMovie);
        when(movieService.getMovie(any())).thenReturn(newMovie);
        this.mockMvc.perform(get("/movies/{movieId}", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newMovie.getId()))
                .andExpect(jsonPath("$.title").value(newMovie.getTitle()))
                .andExpect(jsonPath("$.genres[0]").value(newMovie.getGenres().get(0)))
                .andExpect(jsonPath("$.genres[1]").value(newMovie.getGenres().get(1)));

    }

    @Test
    public void shouldDeleteMovieById() throws Exception {

        this.mockMvc.perform(delete("/movies/{movieId}", 100))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldUpdateMovieById() throws Exception {
        when(movieService.updateMovie(any(Movie.class))).thenReturn(newMovie);
        this.mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMovie)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(newMovie.getId()))
                .andExpect(jsonPath("$.title").value(newMovie.getTitle()))
                .andExpect(jsonPath("$.genres[0]").value(newMovie.getGenres().get(0)));

    }
}
