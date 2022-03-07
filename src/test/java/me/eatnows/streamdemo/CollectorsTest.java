package me.eatnows.streamdemo;

import me.eatnows.streamdemo.filter.Order;
import me.eatnows.streamdemo.partitioning.EmailService;
import me.eatnows.streamdemo.partitioning.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CollectorsTest {

    @Test
    void test1() {
        List<Integer> numberList = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.toList());
        System.out.println(numberList);

        Set<Integer> numberSet = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.toSet());
        System.out.println(numberSet);

        // 절대값
        List<Integer> numberList2 = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toList()));

        System.out.println(numberList2);

        Set<Integer> numberSet2 = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toSet()));

        System.out.println(numberSet2);

        int sum = Stream.of(3, 5, -3, 3, 4, 5)
                .collect(Collectors.reducing(0, (x, y) -> x + y));
        assertThat(sum).isEqualTo(17);
    }

    @Test
    void toMapTest1() {
        //Function.identity() : x -> x 와 같이 단순한 형태일 경우
        Map<Integer, String> numberMap = Stream.of(3, 5, -4, 2, 6)
                .collect(Collectors.toMap(Function.identity(), x -> "Number is " + x));
        System.out.println(numberMap.get(3));
    }

    @Test
    void toMapTest2() {
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

        Map<Integer, User> userIdToUserMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        assertThat(userIdToUserMap.get(101)).isEqualTo(user1);
        assertThat(userIdToUserMap.get(102)).isEqualTo(user2);
        assertThat(userIdToUserMap.get(103)).isEqualTo(user3);
    }

    @Test
    void toMapTest3() {
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

        Map<Long, Order.OrderStatus> orderIdToOrderStatusMap = orders.stream()
                .collect(Collectors.toMap(Order::getId, Order::getStatus));

        assertThat(orderIdToOrderStatusMap.get(1001L)).isEqualTo(Order.OrderStatus.CREATED);
        assertThat(orderIdToOrderStatusMap.get(1002L)).isEqualTo(Order.OrderStatus.ERROR);
        assertThat(orderIdToOrderStatusMap.get(1003L)).isEqualTo(Order.OrderStatus.ERROR);
        assertThat(orderIdToOrderStatusMap.get(1004L)).isEqualTo(Order.OrderStatus.PROCESSED);
    }

    @Test
    void groupingByTest1() {
        List<Integer> numbers = Arrays.asList(13, 2, 101, 203, 304, 402, 305, 349, 2312, 203);
        Map<Integer, List<Integer>> unitDigitMap = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10));
        assertThat(unitDigitMap.get(1).size()).isEqualTo(1);
        assertThat(unitDigitMap.get(2).size()).isEqualTo(3);
        assertThat(unitDigitMap.get(3).size()).isEqualTo(3);
        assertThat(unitDigitMap.get(4).size()).isEqualTo(1);
        assertThat(unitDigitMap.get(5).size()).isEqualTo(1);
        assertThat(unitDigitMap.get(9).size()).isEqualTo(1);

        Map<Integer, Set<Integer>> unitDigitSet = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10, Collectors.toSet()));
        assertThat(unitDigitSet.get(1).size()).isEqualTo(1);
        assertThat(unitDigitSet.get(2).size()).isEqualTo(3);
        assertThat(unitDigitSet.get(3).size()).isEqualTo(2);
        assertThat(unitDigitSet.get(4).size()).isEqualTo(1);
        assertThat(unitDigitSet.get(5).size()).isEqualTo(1);
        assertThat(unitDigitSet.get(9).size()).isEqualTo(1);
    }

    @Test
    void groupingByTest2() {
        List<Integer> numbers = Arrays.asList(13, 2, 101, 203, 304, 402, 305, 349, 2312, 203);
        Map<Integer, List<String>> unitDigitStrMap = numbers.stream()
                .collect(Collectors.groupingBy(number -> number % 10, Collectors.mapping(number -> "unit digit is " + number, Collectors.toList())));

        System.out.println(unitDigitStrMap);
    }

    @Test
    void groupingByTest3() {
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

        Map<Order.OrderStatus, List<Order>> orderStatusMap = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus));

        System.out.println(orderStatusMap);
    }

    @Test
    void groupingByTest4() {
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

        Map<Order.OrderStatus, BigDecimal> orderStatusToSumOfAmountMap = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus,
                        Collectors.mapping(Order::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        System.out.println(orderStatusToSumOfAmountMap);
    }

    @Test
    void partitioningByTest1() {
        List<Integer> numbers = Arrays.asList(13, 2, 101, 203, 304, 402, 305, 349, 2312, 203);
        Map<Boolean, List<Integer>> numberPartitions = numbers.stream()
                .collect(Collectors.partitioningBy(number -> number % 2 == 0));
        System.out.println("Even number: " + numberPartitions.get(true));
        System.out.println("Odd number: " + numberPartitions.get(false));
    }

    @Test
    void partitioningByTest2() {
        User user1 = new User()
                .setId(101)
                .setName("Apple")
                .setEmailAddress("apple@email.com")
                .setFriendUserIds(Arrays.asList(201, 202, 203, 204, 211, 212, 213, 214));
        User user2 = new User()
                .setId(102)
                .setName("Kiwi")
                .setVerified(false)
                .setEmailAddress("kiwi@email.com")
                .setFriendUserIds(Arrays.asList(204, 205, 206));
        User user3 = new User()
                .setId(103)
                .setName("Banana")
                .setVerified(false)
                .setEmailAddress("banana@email.com")
                .setFriendUserIds(Arrays.asList(204, 205, 206, 218));
        List<User> users = Arrays.asList(user1, user2, user3);

        Map<Boolean, List<User>> userPartitions = users.stream()
                .collect(Collectors.partitioningBy(user -> user.getFriendUserIds().size() > 5));

        EmailService emailService = new EmailService();

        for (User user : userPartitions.get(true)) {
            emailService.sendPlayWithFriendsEmail(user);
        }
        for (User user : userPartitions.get(false)) {
            emailService.sendMakeMoreFriendsEmail(user);
        }
    }
}
