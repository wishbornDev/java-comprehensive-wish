package com.wish.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Movie {
    private BigInteger movieId; // 영화 아이디
    private String title;   // 영화제목
    private int ageLimit;   // 연령제한
    private LocalDateTime playTime;     // 상영날짜시간
    private int personnel;  // 예약정원
}
