package com.wish.service;

import com.wish.domain.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserService {

    public void enrollUser(User user) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.txt"))) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
