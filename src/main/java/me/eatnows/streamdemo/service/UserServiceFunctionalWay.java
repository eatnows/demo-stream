package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.User;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class UserServiceFunctionalWay {
    private final Predicate<User> validateUser;
    private final Consumer<User> writeToDB;

    public UserServiceFunctionalWay(Predicate<User> validateUser, Consumer<User> writeToDB) {
        this.validateUser = validateUser;
        this.writeToDB = writeToDB;
    }

    public void createUser(User user) {
        if (validateUser.test(user)) {
            writeToDB.accept(user);
        } else {
            System.out.println("Cannot create user");
        }
    }
}
