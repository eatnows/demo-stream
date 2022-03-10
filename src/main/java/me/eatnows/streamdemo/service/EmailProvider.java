package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.User;

public interface EmailProvider {
    String getEmail(User user);
}
