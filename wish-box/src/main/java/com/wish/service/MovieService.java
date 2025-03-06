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
    
    public List<Movie> playingMovies() throws Exception{

        List<Movie> allMovies = new ArrayList<>(Arrays.asList(allMovies()));
        
        return allMovies.stream()
                        .filter(m -> m.getPlayTime().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());
        
    }

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

    public void insertMovie(Movie[] movies) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("movie.txt"));
        oos.writeObject(movies);

    }
}
