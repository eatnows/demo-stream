package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.User;

public class MakeMoreFriendsEmailProvider implements EmailProvider {

    @Override
    public String getEmail(User user) {
        return "'Make More Friends' email for " + user.getName();
    }
}
