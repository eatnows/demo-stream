package me.eatnows.streamdemo;

import me.eatnows.streamdemo.partitioning.EmailService;
import me.eatnows.streamdemo.partitioning.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ForEachTest {

    @Test
    void test1() {
        List<Integer> numbers = Arrays.asList(3, 5, 2, 1);
        numbers.stream().forEach(number -> System.out.println("The number is " + number));
        numbers.forEach(number -> System.out.println("The number is " + number));
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
        List<User> users = Arrays.asList(user1, user2, user3);

        EmailService emailService = new EmailService();

        users.stream()
                .filter(user -> !user.isVerified())
                .forEach(emailService::sendVerifyYourEmailEmail);
    }

    @Test
    void test3() {
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
        List<User> users = Arrays.asList(user1, user2, user3);

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println("Do an operation on user " + user.getName() + " at index " + i);
        }

        // 시작점은 포함, 끝점은 포함하지 않는다.
        IntStream.range(0, users.size()).forEach(i -> {
            User user = users.get(i);
            System.out.println("Do an operation on user " + user.getName() + " at index " + i);
        });
    }
}
