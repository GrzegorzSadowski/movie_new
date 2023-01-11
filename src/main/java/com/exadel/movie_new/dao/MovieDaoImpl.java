package com.exadel.movie_new.dao;

import com.exadel.movie_new.exception.FilesException;
import com.exadel.movie_new.model.Movie;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao{
    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public List<Movie> read() {
        List<Movie> movies = new ArrayList<>();

        try {

            String json = Files.readString(Path.of("movies.json"));

            movies = gson.fromJson(json, new TypeToken<List<Movie>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
            throw new FilesException("something went wrong during reading file " + e.getMessage());
        }
        return movies;
    }

    public void write(List<Movie> movieData) {


        try (FileWriter file = new FileWriter("movies.json")) {
            file.write(gson.toJson(movieData));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FilesException("something went wrong during writing file " + e.getMessage());
        }
    }

}