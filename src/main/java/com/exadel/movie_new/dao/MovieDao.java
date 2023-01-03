package com.exadel.movie_new.dao;

import com.exadel.movie_new.model.Movie;

import java.io.File;
import java.util.List;

public interface MovieDao {

    List<Movie> read();

     void write(List<Movie> movieData);
}
