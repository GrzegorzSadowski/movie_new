package com.exadel.movie_new.service;

import com.exadel.movie_new.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {


    @Override
    public Movie addMovie(Movie movie) {
        List<Movie> movieData = readJsonData();
        Movie moviePresent = null;

        if (movieData != null) {
            moviePresent = movieData.parallelStream().filter(mov -> mov.getId().equals(movie.getId())).findAny().orElse(null);

        } else {
            movieData = new ArrayList<>();
        }
        if (moviePresent == null) {
            movieData.add(movie);
            writeJsonData(movieData);
        }
        return movie;
    }


    @Override
    public Movie getMovieDetails(String movieId) {
        List<Movie> movieData = readJsonData();
        return movieData.parallelStream().filter(mov -> mov.getId().equals(movieId)).findAny().orElse(null);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        List<Movie> movieData = readJsonData();
        movieData.removeIf(mov -> mov.getId().equals(movie.getId()));
        movieData.add(movie);
        writeJsonData(movieData);
        return movie;
    }

    @Override
    public void deleteMovie(String movieId) {
        List<Movie> movieData = readJsonData();
        movieData.removeIf(mov -> mov.getId().equals(movieId));
        writeJsonData(movieData);

    }

    public List<Movie> readJsonData() {
        List<Movie> movies = new ArrayList<>();
        try {
            String json = Files.readString(Path.of("movies.json"));
            movies = new Gson().fromJson(json, new TypeToken<List<Movie>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }


    public void writeJsonData(List<Movie> movieData) {

        try (FileWriter file = new FileWriter("movies.json")) {
            file.write(new Gson().toJson(movieData));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
