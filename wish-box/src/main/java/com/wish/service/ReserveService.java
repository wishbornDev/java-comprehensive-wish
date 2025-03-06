package com.wish.service;

import com.wish.model.Reservation;
import com.wish.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReserveService {

    private static MovieService movieService = new MovieService();

    public Reservation[] getReserveList() throws Exception {
        File file = new File("reserve.txt");

        if(!file.exists()) file.createNewFile();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        }catch (EOFException e) {
            return null;
        }
        return (Reservation[]) ois.readObject();
    }

    public List<Reservation> checkReserve(User user) throws Exception {

        List<Reservation> reserveList = new ArrayList<>();
        try {
            reserveList = new ArrayList<>(Arrays.asList(getReserveList()));
        }catch (NullPointerException e) {

        }finally {
            return reserveList.stream().filter(r -> r.getReserv_user().equals(user.getUserId())).collect(Collectors.toList());
        }
    }

    public void cancelReserve(int reserveNo) throws Exception{

        List<Reservation> reserveList = new ArrayList<>(Arrays.asList(getReserveList()));
        reserveList.remove(reserveNo);

        Reservation[] reserveArr = reserveList.toArray(new Reservation[reserveList.size()]);

        writeReservaton(reserveArr);

        System.out.println("예매가 취소되었습니다.");

    }

    public boolean reserveMovie(Reservation reservation) throws Exception {

        List<Reservation> reserveList = new ArrayList<>();

        try {
            reserveList = new ArrayList<>(Arrays.asList(getReserveList()));
        }catch (NullPointerException e) {

        } finally {
            reserveList.add(reservation);
        }
        Reservation[] reserveArr = reserveList.toArray(new Reservation[reserveList.size()]);
        writeReservaton(reserveArr);
        movieService.updateMoviePersonnel(reservation);
        return true;
    }

    public void writeReservaton(Reservation[] reservations) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reserve.txt"));

        oos.writeObject(reservations);
    }
}
