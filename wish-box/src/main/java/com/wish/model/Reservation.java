package com.wish.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reservation extends Movie implements Serializable {
    private String reserv_user;
    private int reserv_adult;
    private int reserv_minor;


    public Reservation(int movieId, String title, int ageLimit, LocalDateTime playTime, String reserv_user, int reserv_adult, int reserv_minor) {
        super(movieId, title, ageLimit, playTime);
        this.reserv_user = reserv_user;
        this.reserv_adult = reserv_adult;
        this.reserv_minor = reserv_minor;
    }

    public String getReserv_user() {
        return reserv_user;
    }

    public void setReserv_user(String reserv_user) {
        this.reserv_user = reserv_user;
    }

    public int getReserv_adult() {
        return reserv_adult;
    }

    public void setReserv_adult(int reserv_adult) {
        this.reserv_adult = reserv_adult;
    }

    public int getReserv_minor() {
        return reserv_minor;
    }

    public void setReserv_minor(int reserv_minor) {
        this.reserv_minor = reserv_minor;
    }

    @Override
    public String toString() {
        return "영화 제목 : " + super.getTitle() +
                ", 상영 시간 : " + super.getPlayTime() +
                ", 연령 제한 : " + super.getAgeLimit() +
                ", 성인 : " + reserv_adult +
                ", 아동/청소년 : " + reserv_minor;
    }
}
