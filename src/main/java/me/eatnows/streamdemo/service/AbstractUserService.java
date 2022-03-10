package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.User;

public abstract class AbstractUserService {
    protected abstract boolean validateUser(User user);

    protected abstract void writeToDB(User user);

    // 이 과정에 대한 뼈대만 제공
    // 어떤식으로 유저를 검증을 하고 어떤식으로 DB에 쓸것인지는 하위 클래스들이 정의를 하면된다.
    // 이 알고리즘에 뼈대는 상위 클래스에서 정의를 한다.
    public void createUser(User user) {
        if (validateUser(user)) {
            writeToDB(user);
        } else {
            System.out.println("Cannot create user");
        }
    }
}
