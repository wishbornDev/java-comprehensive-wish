package com.wish.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Movie implements Serializable {
    private int movieId; // 영화 아이디
    private String title;   // 영화제목
    private int ageLimit;   // 연령제한
    private LocalDateTime playTime;     // 상영날짜시간
    private int personnel;  // 예약정원

    public Movie(int movieId, String title, int ageLimit, LocalDateTime playTime, int personnel) {
        this.movieId = movieId;
        this.title = title;
        this.ageLimit = ageLimit;
        this.playTime = playTime;
        this.personnel = personnel;
    }

    public Movie(int movieId, String title, int ageLimit, LocalDateTime playTime) {
        this.movieId = movieId;
        this.title = title;
        this.ageLimit = ageLimit;
        this.playTime = playTime;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public LocalDateTime getPlayTime() {
        return playTime;
    }

    public void setPlayTime(LocalDateTime playTime) {
        this.playTime = playTime;
    }

    public int getPersonnel() {
        return personnel;
    }

    public void setPersonnel(int personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "영화제목 : " + title
                + ", 연령 제한 : " + ageLimit 
                + ", 상영 시간 : " + playTime
                + ", 예매 가능 좌석 : " + personnel ;
    }
}
