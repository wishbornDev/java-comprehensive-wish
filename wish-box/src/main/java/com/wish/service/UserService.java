package com.wish.service;

import com.wish.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static ReserveService reserveService;

    /**
     * 회원 정보 조회
     * @return User[]
     */
    public static User[] readUser() throws Exception {

        File file = new File("user.txt");

        if(!file.exists()) file.createNewFile();
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        }catch (EOFException e) {
            return null;
        }
        return (User[]) ois.readObject();
    }

    /**
     * 회원 가입
     * @param user
     */
    public void enrollUser(User user) throws Exception{

        List<User> users = new ArrayList<>();
        try {
            users = new ArrayList<>(Arrays.asList(readUser()));
        }catch (NullPointerException e) {

        } finally {
            users.add(user);
        }
        User[] userArr = users.toArray(new User[users.size()]);

        insertUser(userArr);
    }

    /**
     * 로그인
     * @param user
     * @return boolean
     */
    public boolean loginUser(User user) throws Exception{

        List<User> users = new ArrayList<>();

        try {
            users.addAll(Arrays.asList(readUser()));
        }catch (NullPointerException e) {
            System.out.println("가입된 회원이 아닙니다. 회원가입을 진행해주세요.");
            return false;
        }

        boolean isUser = users.stream()
                .filter(u -> u.getUserId().equals(user.getUserId()))
                .anyMatch(u -> u.getUserPwd().equals(user.getUserPwd()));
        if(!isUser) {
            System.out.println("아이디 또는 비밀번호가 맞지 않습니다.");
            return false;
        } else return true;
    }

    /**
     * ID 중복 확인
     * @param userId
     * @return String
     */
    public boolean validUserId(String userId) throws Exception {

        List<User> users = new ArrayList<>();try {
            users.addAll(Arrays.asList(readUser()));
        }catch (NullPointerException e) {
            return true;
        }

        List<String> list = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .map(User::getUserId)
                .collect(Collectors.toList());

        if (list.isEmpty() || list == null) {
            return true;
        }

        return false;
    }

    /**
     * 회원 정보 insert
     */
    public void insertUser(User[] users) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.txt"));
        oos.writeObject(users);
        System.out.println("회원가입이 완료되었습니다!");
    }
}
