package com.wish.model;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private String userId;
    private String userPwd;
    private String userName;
    private LocalDate birthDay;
    private boolean isDeleted;

    public User() {
    }

    public User(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

    public User(String userId, String userPwd, String userName, LocalDate birthDay, boolean isDeleted) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.userName = userName;
        this.birthDay = birthDay;
        this.isDeleted = isDeleted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userName='" + userName + '\'' +
                ", birthDay=" + birthDay +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
