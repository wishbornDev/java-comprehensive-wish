package com.wish.controller;

import com.wish.model.User;
import com.wish.service.UserService;

import java.time.LocalDate;

public class UserController {
    private static UserService userService = new UserService();

    /**
     * 회원 가입
     * @param user
     */
    public void enrollUser(User user) throws Exception {
        userService.enrollUser(user);
    }

    /**
     * 로그인
     * @param user
     * @return boolean
     */
    public boolean loginUser(User user) throws Exception{

       return userService.loginUser(user);

    }

    /**
     * ID 중복 확인
     * @param userId
     * @return String
     */
    public String validUserId(String userId) throws Exception {

        boolean isValid = userService.validUserId(userId);

        if (isValid) return userId;
        else {
            return "중복된 아이디 입니다. 다시 입력해주세요.";
        }
    }

    /**
     * 회원 정보 insert
     */
    public void insertUser() throws Exception {

        User[] users = {
                         new User("user01", "pwd01", "구병모", LocalDate.of(1976, 12, 10), false),
                         new User("user02", "pwd02", "천선란", LocalDate.of(1993, 8, 8), false),
                         new User("user03", "pwd03", "김초엽", LocalDate.of(1993, 4, 4), false),
                         new User("user04", "pwd04", "정세랑", LocalDate.of(2006, 10, 15), false),
                        };

        userService.insertUser(users);
    }
}
