package com.wish.controller;

import com.wish.model.Movie;
import com.wish.service.MovieService;

import java.time.LocalDateTime;
import java.util.List;

public class MovieController {

    private static MovieService movieService = new MovieService();


    public List<Movie> playingMovies() throws Exception {

        return movieService.playingMovies();
    }

    public Movie getMovieInfo(int movieNo) throws Exception {

        return movieService.getMovieInfo(movieNo);
    }

    public void insertMovie() throws Exception {

        Movie[] movies = {
                            new Movie(1, "캡틴아메리카", 12, LocalDateTime.of(2025, 2, 14, 20,8), 97),
                            new Movie(2, "미키17", 15, LocalDateTime.of(2025, 2, 14, 21,10), 88),
                            new Movie(3, "캡틴아메리카", 12, LocalDateTime.of(2025, 2, 15, 12,0),90),
                            new Movie(4, "패딩턴", 0, LocalDateTime.of(2025, 2, 15, 13,0), 80),
                            new Movie(5, "미키17", 15, LocalDateTime.of(2025, 3, 6, 18,9),100),
                            new Movie(6, "미키17", 15, LocalDateTime.of(2025, 3, 8, 10,10), 79),
                            new Movie(7, "패딩턴", 0, LocalDateTime.of(2025, 3, 15, 12,0), 90)
                         };

        movieService.insertMovie(movies);
    }
}
