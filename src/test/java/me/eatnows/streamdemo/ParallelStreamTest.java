package me.eatnows.streamdemo;

import me.eatnows.streamdemo.partitioning.EmailService;
import me.eatnows.streamdemo.partitioning.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParallelStreamTest {

    @Test
    void test1() {
        User user1 = new User()
                .setId(101)
                .setName("Apple")
                .setVerified(true)
                .setEmailAddress("apple@email.com");
        User user2 = new User()
                .setId(102)
                .setName("Kiwi")
                .setVerified(false)
                .setEmailAddress("kiwi@email.com");
        User user3 = new User()
                .setId(103)
                .setName("Banana")
                .setVerified(false)
                .setEmailAddress("banana@email.com");
        User user4 = new User()
                .setId(104)
                .setName("Orange")
                .setVerified(true)
                .setEmailAddress("orange@email.com");
        User user5 = new User()
                .setId(105)
                .setName("Mango")
                .setVerified(false)
                .setEmailAddress("mango@email.com");
        User user6 = new User()
                .setId(106)
                .setName("Melon")
                .setVerified(false)
                .setEmailAddress("melon@email.com");
        List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6);

        long startTime = System.currentTimeMillis();
        EmailService emailService = new EmailService();
        users.stream()
                .filter(user -> !user.isVerified())
                .forEach(emailService::sendVerifyYourEmailEmail);
        long endTime = System.currentTimeMillis();
        System.out.println("Sequential: " + (endTime - startTime) + "ms");


        startTime = System.currentTimeMillis();
        users.stream().parallel()
                .filter(user -> !user.isVerified())
                .forEach(emailService::sendVerifyYourEmailEmail);
        endTime = System.currentTimeMillis();
        System.out.println("Parallel: " + (endTime - startTime) + "ms");


    }

    @Test
    void test2() {
        User user1 = new User()
                .setId(101)
                .setName("Apple")
                .setVerified(true)
                .setEmailAddress("apple@email.com");
        User user2 = new User()
                .setId(102)
                .setName("Kiwi")
                .setVerified(false)
                .setEmailAddress("kiwi@email.com");
        User user3 = new User()
                .setId(103)
                .setName("Banana")
                .setVerified(false)
                .setEmailAddress("banana@email.com");
        User user4 = new User()
                .setId(104)
                .setName("Orange")
                .setVerified(true)
                .setEmailAddress("orange@email.com");
        User user5 = new User()
                .setId(105)
                .setName("Mango")
                .setVerified(false)
                .setEmailAddress("mango@email.com");
        User user6 = new User()
                .setId(106)
                .setName("Melon")
                .setVerified(false)
                .setEmailAddress("melon@email.com");
        List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6);

        List<User> processedUsers = users.parallelStream()
                .map(user -> {
                    System.out.println("Capitalize user name for user " + user.getId());
                    user.setName(user.getName().toUpperCase());
                    return user;
                })
                .map(user -> {
                    System.out.println("Set 'isVerified' to true for user " + user);
                    user.setVerified(true);
                    return user;
                })
                .collect(Collectors.toList());
        System.out.println(processedUsers);
    }
}
