package com.wish.controller;

import com.wish.model.Reservation;
import com.wish.model.User;
import com.wish.service.ReserveService;

import java.time.LocalDateTime;
import java.util.List;

public class ReserveController {

    private static ReserveService reserveService = new ReserveService();

    /**
     * 예매 내역 확인
     * @param user
     * @return List<Reservation
     */
    public List<Reservation> checkReserve(User user) throws Exception {

        return reserveService.checkReserve(user);
    }

    /**
     * 예매 취소
     * @params reserveNo
     */
    public void cancelReserve(int reserveNo) throws  Exception {

        reserveService.cancelReserve(reserveNo);
    }

    /**
     * 영화 예매
     * @param reservation
     * @return boolean
     */
    public boolean reserveMovie(Reservation reservation) throws  Exception {

        return reserveService.reserveMovie(reservation);

    }

    /**
     * 예매 내역 insert
     */
    public void insertReservation() throws Exception  {

        Reservation[] reservations = {
                                        new Reservation(1, "캡틴아메리카", 12
                                                , LocalDateTime.of(2025, 2, 14, 20,8), "user01", 2, 1),
                                        new Reservation(2, "미키17", 15
                                                , LocalDateTime.of(2025, 2, 14, 21,10), "user01", 1, 0),
                                        new Reservation(2, "미키17", 15
                                                , LocalDateTime.of(2025, 2, 14, 21,10),"user02",  1, 0),
                                        new Reservation(6, "미키17", 15
                                                , LocalDateTime.of(2025, 3, 8, 10,10), "user02", 2, 0),
                                     };

        reserveService.writeReservaton(reservations);

    }
}
