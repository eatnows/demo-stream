package me.eatnows.streamdemo;

import me.eatnows.streamdemo.filter.Order;
import me.eatnows.streamdemo.filter.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamTest {


    @Test
    void test1() {
        Stream<String> nameStream = Stream.of("Apple", "Kiwi", "Banana");
        List<String> names = nameStream.collect(Collectors.toList());
        System.out.println(names);

        String[] cityArray = new String[]{"Apple", "Samsung", "Google"};
        Stream<String> cityStream = Arrays.stream(cityArray);
        List<String> cityList = cityStream.collect(Collectors.toList());
        System.out.println(cityList);
    }

    @Test
    void test2() {
        Set<Integer> numberSet = new HashSet<>(Arrays.asList(3, 5, 7));
        Stream<Integer> numberStream = numberSet.stream();
        List<Integer> numberList = numberStream.collect(Collectors.toList());
        assertThat(numberList.get(0)).isEqualTo(3);
        assertThat(numberList.get(1)).isEqualTo(5);
        assertThat(numberList.get(2)).isEqualTo(7);
    }

    @Test
    void filterTest1() {
        Stream<Integer> numberStream = Stream.of(3, -5, 7, 10, -3);
        Stream<Integer> filteredNumberStream = numberStream.filter(x -> x > 0);
        List<Integer> filteredNumbers = filteredNumberStream.collect(Collectors.toList());

        assertThat(filteredNumbers.get(0)).isEqualTo(3);
        assertThat(filteredNumbers.get(1)).isEqualTo(7);
        assertThat(filteredNumbers.get(2)).isEqualTo(10);

        List<Integer> newFilteredNumbers = Stream.of(3, -5, 7, 10, -3)
                .filter(x -> x > 0)
                .collect(Collectors.toList());

        assertThat(newFilteredNumbers.get(0)).isEqualTo(3);
        assertThat(newFilteredNumbers.get(1)).isEqualTo(7);
        assertThat(newFilteredNumbers.get(2)).isEqualTo(10);
    }

    @Test
    void filterTest2() {
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
        List<User> verifiedUsers = users.stream()
                .filter(User::isVerified)
                .collect(Collectors.toList());

        assertThat(verifiedUsers.get(0).getId()).isEqualTo(101);
        assertThat(verifiedUsers.size()).isEqualTo(1);

        List<User> unVerifiedUsers = users.stream()
                .filter(user -> !user.isVerified())
                .collect(Collectors.toList());

        assertThat(unVerifiedUsers.get(0).getId()).isEqualTo(102);
        assertThat(unVerifiedUsers.get(1).getId()).isEqualTo(103);
        assertThat(unVerifiedUsers.size()).isEqualTo(2);
    }

    @Test
    void filterTest3() {
        Order order1 = new Order()
                .setId(1001)
                .setStatus(Order.OrderStatus.CREATED);
        Order order2 = new Order()
                .setId(1002)
                .setStatus(Order.OrderStatus.ERROR);
        Order order3 = new Order()
                .setId(1003)
                .setStatus(Order.OrderStatus.PROCESSED);
        Order order4 = new Order()
                .setId(1004)
                .setStatus(Order.OrderStatus.ERROR);
        Order order5 = new Order()
                .setId(1005)
                .setStatus(Order.OrderStatus.IN_PROGRESS);

        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
        List<Order> errorOrders = orders.stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.ERROR)
                .collect(Collectors.toList());

        assertThat(errorOrders.get(0).getId()).isEqualTo(1002);
        assertThat(errorOrders.get(1).getId()).isEqualTo(1004);
        assertThat(errorOrders.size()).isEqualTo(2);

    }
}
