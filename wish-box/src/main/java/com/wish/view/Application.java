package com.wish.view;

import com.wish.controller.MovieController;
import com.wish.controller.ReserveController;
import com.wish.controller.UserController;
import com.wish.model.Movie;
import com.wish.model.Reservation;
import com.wish.model.User;
import com.wish.service.UserService;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Application {

    private static UserController userController = new UserController();
    private static ReserveController reserveController = new ReserveController();
    private static MovieController movieController = new MovieController();

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static boolean isUser = false;
    private static User loginUser;


    public static void main(String[] args) {

        /* 초기 셋팅 */
        /*try {
            //userController.insertUser();
            movieController.insertMovie();
            //reserveController.insertReservation();

            ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream("user.txt"));
            List<User> users = Arrays.asList((User[]) ois1.readObject());

            for(User u :users) {
                System.out.println(u.toString());
            }

            ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream("reserve.txt"));
            List<Reservation> reservations = Arrays.asList((Reservation[]) ois2.readObject());

            for(Reservation r :reservations) {
                System.out.println(r.toString());
            }

            ObjectInputStream ois3 = new ObjectInputStream(new FileInputStream("movie.txt"));
            List<Movie> movies = Arrays.asList((Movie[])ois3.readObject());

            for(Movie m :movies) {
                System.out.println(m.toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/

       mainHome();
    }

    private static void mainHome() {
        // 영화 예약 시스템
        StringBuilder sb = new StringBuilder();

        System.out.println("********* WISH BOX **********");
        System.out.println("1. 회원가입");
        if (!isUser) System.out.println("2. 로그인");
        System.out.println("9. 프로그램 종료");
        System.out.print(" : ");
        try {
            int systemNo = Integer.parseInt(br.readLine());

            switch (systemNo) {
                case 1 -> enrollUser();
                case 2 -> loginUser();
                case 9 -> System.out.println("프로그램을 종료합니다 !");
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요. ");
                    mainHome();
                }
            }
            br.close();
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요. ");
            mainHome();
        } catch (Exception e) {
            System.out.println("오류 : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void enrollUser() throws Exception {
        System.out.println("********* 회원가입 **********");
        String userId;
        try {
            while (true) {
                System.out.print("아이디 : ");
                String inputId = br.readLine();
                userId = userController.validUserId(inputId);
                if (!inputId.equals(userId)) {
                    System.out.println(userId);
                    continue;
                } else break;
            }

            System.out.print("비밀번호 : ");
            String pwd = br.readLine();
            System.out.print("이름 : ");
            String name = br.readLine();
            System.out.print("생년월일(ex 19991231) : ");
            LocalDate birthDay = LocalDate.parse(br.readLine(), DateTimeFormatter.BASIC_ISO_DATE);

            System.out.print("회원가입을 완료하시겠습니까? y/n : ");
            String flag = br.readLine();
            if (flag.equalsIgnoreCase("y")) {
                User user = new User(userId, pwd, name, birthDay, false);
                userController.enrollUser(user);
            } else if (flag.equalsIgnoreCase("n")) mainHome();
        } catch (InputMismatchException e) {
            System.out.println("잘못된 값을 입력하셨습니다. 메인으로 돌아갑니다.");
            mainHome();
        }
    }


    private static void loginUser() throws Exception {

        System.out.println("********* 로그인 **********");
        System.out.print("아이디 : ");
        String userId = br.readLine();
        System.out.print("비밀번호 : ");
        String pwd = br.readLine();

        User user = new User(userId, pwd);

        isUser = userController.loginUser(user);

        if (isUser) {
            loginUser = user;
            process();
        }
        else mainHome();

    }

    private static void process() throws Exception {
        System.out.println("********* 영화 예매 **********");
        System.out.println("1. 예매 확인");
        System.out.println("2. 영화 예매");
        System.out.println("3. 로그아웃");
        System.out.print(" : ");

        try {
            int systemNo = Integer.parseInt(br.readLine());

            switch (systemNo) {
                case 1 -> checkReserve();
                case 2 -> playingMovie();
                case 3 -> {
                    loginUser = null;
                    isUser = false;
                    mainHome();
                }
                default -> {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요. ");
                    process();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요. ");
            process();
        }
    }

    private static void checkReserve() throws Exception{

        List<Reservation> reserveList = reserveController.checkReserve(loginUser);

        System.out.println("********* 예매 내역 **********");
        for (int i = 1; i <= reserveList.size(); i++) {
            System.out.println("예매번호 : " + i + ", " + reserveList.get(i-1).toString());
        }

        if (reserveList.isEmpty()) {
            System.out.println("## 예매 내역이 없습니다.");
            System.out.println("## 뒤로가시려면 'return'을 입력해주세요.");
        } else {
            System.out.println("## 예매를 취소하시려면 예매 번호를 입력해주세요.");
            System.out.println("## 뒤로가시려면 'return'을 입력해주세요.");
        }
        String system = br.readLine();
        try {
            if(system.equalsIgnoreCase("return")) process();
            else {
                int systemNo = Integer.parseInt(system);
                reserveController.cancelReserve(systemNo-1);

                checkReserve();
            }
        } catch (NumberFormatException e) {
            System.out.println("잘못된 값을 입력하셨습니다.");
            checkReserve();
        }
    }

    private static void playingMovie() throws Exception {
        List<Movie> playingMovies = movieController.playingMovies();

        System.out.println("********* 상영 중인 영화 **********");
        for (int i = 0; i < playingMovies.size(); i++) {
            System.out.println("영화 번호 : " + playingMovies.get(i).getMovieId() + ", " + playingMovies.get(i).toString());
        }

        System.out.println("## 영화를 예매하시려면 영화의 번호를 입력해주세요.");
        System.out.println("## 뒤로가시려면 'return'을 입력해주세요.");

        String system = br.readLine();
        try{
            if(system.equalsIgnoreCase("return")) process();
            else {
                int movieId = Integer.parseInt(system);
                reserveMovie(movieId);
            }
        } catch (NumberFormatException e) {
            System.out.println("잘못된 값을 입력하셨습니다.");
            playingMovie();
        } catch (NullPointerException e) {
            System.out.println("현재 상영 중인 영화가 아닙니다. ");
            playingMovie();
        }
    }

    private static void reserveMovie(int movieId) throws Exception {

        Movie movie = movieController.getMovieInfo(movieId);

        if (movie.getPersonnel() == 0) {
            System.out.println("예매 가능한 좌석이 없습니다.");
            playingMovie();
        }
        char flag = 'n';
        int adult = 0;
        int minor = 0;

        while (flag == 'n') {

            System.out.println("********* 예매 하기 **********");
            System.out.println(movie.toString());
            System.out.println("## 예매 인원을 입력해주세요.");
            System.out.println("## 한 영화 당 최대 예매 가능한 인원은 5명 입니다. ");
            System.out.print("1. 성인 : ");
            adult = Integer.parseInt(br.readLine());
            System.out.print("2. 아동/청소년 : ");
            minor = Integer.parseInt(br.readLine());

            if (movie.getAgeLimit() == 19 && adult == 0) {
                System.out.println("미성년자 관람 불가 영화입니다. 보호자를 동반해주세요.");
                continue;
            }

            else if (adult + minor == 0) {
                System.out.println("예매할 인원을 입력해주세요.");
                continue;
            }

            else if (adult + minor > 5) {
                System.out.println("예매 가능 인원을 초과하였습니다.");
                continue;
            }

            else {
                System.out.println("성인 : " + adult +", 아동/청소년 : " + minor);
                System.out.println("예매 정보가 맞으시면 'y' \n다시 입력하시려면 'n' \n영화 목록으로 돌아가시려면 'r' 을 입력해주세요.");
                System.out.print(": ");
                flag = br.readLine().charAt(0);

            }
        }
        boolean isReserved = false;

        if (flag == 'y') {

            Reservation reservation = new Reservation(movieId, movie.getTitle(), movie.getAgeLimit(), movie.getPlayTime()
                    , loginUser.getUserId(), adult, minor);
            isReserved = reserveController.reserveMovie(reservation);
        }
        else if (flag == 'r') playingMovie();
        else {
            System.out.println("잘못된 값을 입력하셨습니다. 예매 페이지로 돌아갑니다.");
            reserveMovie(movieId);
        }

        if(isReserved) {
            System.out.println("예매가 완료되었습니다.");
            checkReserve();
        }
    }
}
