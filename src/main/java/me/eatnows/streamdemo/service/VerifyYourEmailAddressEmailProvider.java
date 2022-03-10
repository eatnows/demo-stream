package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.User;

public class VerifyYourEmailAddressEmailProvider implements EmailProvider {
    @Override
    public String getEmail(User user) {
        return "Verify Your Email Address ' email for " + user.getName();
    }
}
