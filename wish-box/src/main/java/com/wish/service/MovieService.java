package com.wish.service;

import com.wish.model.Movie;
import com.wish.model.Reservation;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    /**
     * 전체 영화 목록
     * @return Movie[]
     */
    public Movie[] allMovies() throws Exception {
        File file = new File("movie.txt");

        if(!file.exists()) file.createNewFile();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        }catch (EOFException e) {
            return null;
        }

        return (Movie[])ois.readObject();
    }

    /**
     * 상영 중인 영화
     * @return List<Movie>
     */
    public List<Movie> playingMovies() throws Exception{

        List<Movie> allMovies = new ArrayList<>(Arrays.asList(allMovies()));
        
        return allMovies.stream()
                        .filter(m -> m.getPlayTime().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
        
    }

    /**
     * 영화 예매 / 취소 시 예매 가능 수 변경
     * @param reservation
     */
    public void updateMoviePersonnel(Reservation reservation) throws Exception {

        List<Movie> allMovies = new ArrayList<>(Arrays.asList(allMovies()));

        List<Movie> update = allMovies.stream()
                .filter(m -> m.getMovieId() == reservation.getMovieId())
                .collect(Collectors.toList());

        int index = allMovies.indexOf(update.get(0));
        int adult = reservation.getReserv_adult();
        int minor = reservation.getReserv_minor();
        int personnel = update.get(0).getPersonnel() - (adult + minor);
        update.get(0).setPersonnel(personnel);
        allMovies.set(index, update.get(0));

        Movie[] mvArr = allMovies.toArray(new Movie[allMovies.size()]);

        insertMovie(mvArr);

    }

    /**
     * 영화 정보
     * @param movieNo
     * @return Movie
     */
    public Movie getMovieInfo(int movieNo) throws Exception {
        List<Movie> allMovies = new ArrayList<>();
        allMovies.addAll(Arrays.asList(allMovies()));

        List<Movie> movies = new ArrayList<>();
        for (Movie m : allMovies) {
            if (m.getMovieId() == movieNo) {
                movies.add(m);
            }
        }

        return movies.get(0);
    }

    /**
     * 영화 정보 insert
     * @param movies
     */
    public void insertMovie(Movie[] movies) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("movie.txt"));
        oos.writeObject(movies);

    }
}
