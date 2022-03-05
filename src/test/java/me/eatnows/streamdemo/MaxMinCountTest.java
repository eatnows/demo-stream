package me.eatnows.streamdemo;

import me.eatnows.streamdemo.filter.Order;
import me.eatnows.streamdemo.maxmincount.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MaxMinCountTest {

    @Test
    void test1() {
        Optional<Integer> max1 = Stream.of(5, 3, 6, 2, 1)
                .max((x, y) -> x - y);
        Optional<Integer> max2 = Stream.of(5, 3, 6, 2, 1)
                .max(Integer::compareTo);

        assertThat(max1.get()).isEqualTo(6);
        assertThat(max2.get()).isEqualTo(max1.get());
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

        User firstUser = users.stream()
                .min((u1, u2) -> u1.getName().compareTo(u2.getName()))
                .get();

        assertThat(firstUser.getName()).isEqualTo(user1.getName());
    }

    @Test
    void test3() {
        long positiveIntegerCount = Stream.of(1, -4, 5, -3, 6)
                .filter(x -> x > 0)
                .count();
        assertThat(positiveIntegerCount).isEqualTo(3);
    }

    @Test
    void test4() {
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
                .setId(103)
                .setName("Melon")
                .setVerified(false)
                .setEmailAddress("melon@email.com");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        user1.setCreatedAt(now.minusDays(2));
        user2.setCreatedAt(now.minusHours(10));
        user3.setCreatedAt(now.minusHours(1));
        user4.setCreatedAt(now.minusHours(27));

        List<User> users = Arrays.asList(user1, user2, user3, user4);

        long unverifiedUsersIn24Hrs = users.stream()
                .filter(user -> user.getCreatedAt().isAfter(now.minusDays(1)))
                .filter(user -> !user.isVerified())
                .count();

        assertThat(unverifiedUsersIn24Hrs).isEqualTo(2);
    }

    @Test
    void test5() {
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

        Order erroredOrderWithMaxAmount = orders.stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.ERROR)
                .max((o1, o2) -> o1.getAmount().compareTo(o2.getAmount()))
                .get();

        assertThat(erroredOrderWithMaxAmount.getId()).isEqualTo(1002);
        assertThat(erroredOrderWithMaxAmount).isEqualTo(order2);

        BigDecimal maxErroredAmount = orders.stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.ERROR)
                .map(Order::getAmount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        assertThat(maxErroredAmount).isEqualTo(order2.getAmount());
    }
}
