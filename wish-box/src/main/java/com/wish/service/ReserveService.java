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

    /**
     * 전체 예매내역 조회
     * @return Reservation[]
     */
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

    /**
     * 예매 내역 확인
     * @param user
     * @return List<Reservation
     */
    public List<Reservation> checkReserve(User user) throws Exception {

        List<Reservation> reserveList = new ArrayList<>();
        try {
            reserveList = new ArrayList<>(Arrays.asList(getReserveList()));
        }catch (NullPointerException e) {

        }finally {
            return reserveList.stream().filter(r -> r.getReserv_user().equals(user.getUserId())).collect(Collectors.toList());
        }
    }

    /**
     * 예매 취소
     * @param reserveNo
     */
    public void cancelReserve(int reserveNo) throws Exception{

        List<Reservation> reserveList = new ArrayList<>(Arrays.asList(getReserveList()));
        Reservation reservation = reserveList.get(reserveNo);
        reserveList.remove(reserveNo);

        Reservation[] reserveArr = reserveList.toArray(new Reservation[reserveList.size()]);

        writeReservaton(reserveArr);
        // cancel
        boolean flag = false;
        movieService.updateMoviePersonnel(reservation, flag);

        System.out.println("예매가 취소되었습니다.");

    }

    /**
     * 영화 예매
     * @param reservation
     * @return boolean
     */
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
        // reserve
        boolean flag = true;
        movieService.updateMoviePersonnel(reservation, flag);
        return true;
    }

    /**
     * 예매 내역 insert
     */
    public void writeReservaton(Reservation[] reservations) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reserve.txt"));

        oos.writeObject(reservations);
    }
}
