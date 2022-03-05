package me.eatnows.streamdemo;

import me.eatnows.streamdemo.filter.Order;
import me.eatnows.streamdemo.maxmincount.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchTest {

    @Test
    void allMatchTest() {
        // allMatch: 하나라도 predicate를 만족하지 않으면 false를 반환
        List<Integer> numbers = Arrays.asList(3, -4, 2, 7, 9);
        boolean allPositive = numbers.stream()
                .allMatch(number -> number > 0);

        assertThat(allPositive).isEqualTo(false);
    }

    @Test
    void anyMatchTest() {
        // allMatch: 하나라도 predicate를 만족하면 true를 반환
        List<Integer> numbers = Arrays.asList(3, -4, 2, 7, 9);
        boolean anyNegative = numbers.stream()
                .anyMatch(number -> number < 0);

        assertThat(anyNegative).isEqualTo(true);
    }

    @Test
    @DisplayName("모든 유저가 인증을 받았는지")
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

        boolean areAllUserVerified = users.stream()
                .allMatch(User::isVerified);

        assertThat(areAllUserVerified).isEqualTo(false);
    }

    @Test
    @DisplayName("에러 상태인 Order가 하나라도 있는지")
    void test4() {
        Order order1 = new Order()
                .setId(1001)
                .setAmount(BigDecimal.valueOf(2000))
                .setStatus(Order.OrderStatus.CREATED);
        Order order2 = new Order()
                .setId(1002)
                .setAmount(BigDecimal.valueOf(4000))
                .setStatus(Order.OrderStatus.ERROR);
        Order order3 = new Order()
                .setId(1003)
                .setAmount(BigDecimal.valueOf(3000))
                .setStatus(Order.OrderStatus.ERROR);
        Order order4 = new Order()
                .setId(1004)
                .setAmount(BigDecimal.valueOf(7000))
                .setStatus(Order.OrderStatus.PROCESSED);
        List<Order> orders = Arrays.asList(order1, order2, order3, order4);

        boolean isAnyOrderInErrorStatus = orders.stream()
                .anyMatch(order -> order.getStatus() == Order.OrderStatus.ERROR);

        assertThat(isAnyOrderInErrorStatus).isEqualTo(true);
    }
}
