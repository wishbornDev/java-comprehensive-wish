package com.wish.ui;

import com.wish.domain.User;
import com.wish.service.UserService;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

public class Application {

    private static UserService userService;

    public static void main(String[] args) {

        // 영화 예약 시스템
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();


        System.out.println("********* WISH BOX **********");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 프로그램 종료");

        try {
            int systemNo = Integer.parseInt(br.readLine());

            switch(systemNo) {
                case 1 -> enrollUser();
                case 2 -> loginUser();
                case 3 -> {
                    System.out.println("프로그램을 종료합니다 !");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 입력해주세요. ");
            }

        } catch (Exception e) {
            System.out.println("오류 : " + e.getMessage());
        }
    }

    private static void loginUser() {
    }

    private static void enrollUser() throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("********* 회원 가입 **********");
        String userId = validUserId();
        System.out.print("비밀번호 : ");
        String pwd = br.readLine();
        System.out.print("이름 : ");
        String name = br.readLine();
        System.out.print("생년월일(-제외) : ");
        LocalDate birthDay = LocalDate.parse(br.readLine());

        User user = new User(userId, pwd, name, birthDay, false);

        userService.enrollUser(user);

    }

    private static String validUserId() throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean isValid = false;

        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream("user.txt"));
        ois.readObject();

        User[] users = (User[])ois.readObject();
        System.out.print("아이디 : ");
        String userId = br.readLine();
        isValid = Arrays.stream(users).map(User::getUserId).anyMatch(a -> a.equals(userId));

        if (isValid) return userId;
        else {
            System.out.println(" 중복된 아이디 입니다. 다시 입력해주세요. ");
            return validUserId();
        }
    }
}
