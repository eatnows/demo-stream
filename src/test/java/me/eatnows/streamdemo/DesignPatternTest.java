package me.eatnows.streamdemo;

import me.eatnows.streamdemo.designpattern.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DesignPatternTest {

    @Test
    @DisplayName("Builder Pattern")
    void builderPatternTest() {
//        User user = User.builder(1, "Apple")
//                .withEmailAddress("apple@email.com")
//                .withVerified(true)
//                .build();
//
//        System.out.println(user);

        User user = User.builder(1, "Apple")
                .with(builder -> {
                    builder.emailAddress = "apple@email.com";
                    builder.isVerified = true;
                }).build();

        System.out.println(user);
    }
}
