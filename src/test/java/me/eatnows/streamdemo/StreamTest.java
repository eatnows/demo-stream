package me.eatnows.streamdemo;

import me.eatnows.streamdemo.filter.Order;
import me.eatnows.streamdemo.filter.OrderLine;
import me.eatnows.streamdemo.filter.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
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

    @Test
    void mapTest1() {
        List<Integer> numberList = Arrays.asList(3, 6, -4);
        Stream<Integer> numberStream = numberList.stream();
        Stream<Integer> numberStreamX2 = numberStream.map(x -> x * 2);
        List<Integer> numberListX2 = numberStreamX2.collect(Collectors.toList());

        assertThat(numberListX2.get(0)).isEqualTo(6);
        assertThat(numberListX2.get(1)).isEqualTo(12);
        assertThat(numberListX2.get(2)).isEqualTo(-8);

        List<Integer> numberListX2_2 = numberList.stream()
                .map(x -> x * 2)
                .collect(Collectors.toList());
        assertThat(numberListX2_2.get(0)).isEqualTo(6);
        assertThat(numberListX2_2.get(1)).isEqualTo(12);
        assertThat(numberListX2_2.get(2)).isEqualTo(-8);

        Stream<Integer> numberListStream = numberList.stream();
        Stream<String> stringStream = numberListStream.map(x -> "Number is " + x);
        List<String> stringList = stringStream.collect(Collectors.toList());

        assertThat(stringList.get(0)).isInstanceOf(String.class);
        assertThat(stringList.get(1)).isInstanceOf(String.class);
        assertThat(stringList.get(2)).isInstanceOf(String.class);
    }

    @Test
    void mapTest2() {
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
        List<String> emailAddresses = users.stream()
                .map(User::getEmailAddress)
                .collect(Collectors.toList());

        assertThat(emailAddresses.get(0)).isEqualTo("apple@email.com");
        assertThat(emailAddresses.get(1)).isEqualTo("kiwi@email.com");
        assertThat(emailAddresses.get(2)).isEqualTo("banana@email.com");
    }

    @Test
    void mapTest3() {
        Order order1 = new Order()
                .setId(1001)
                .setStatus(Order.OrderStatus.CREATED)
                .setCreatedByUserId(101);
        Order order2 = new Order()
                .setId(1002)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(103);
        Order order3 = new Order()
                .setId(1003)
                .setStatus(Order.OrderStatus.PROCESSED)
                .setCreatedByUserId(102);
        Order order4 = new Order()
                .setId(1004)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(104);
        Order order5 = new Order()
                .setId(1005)
                .setStatus(Order.OrderStatus.IN_PROGRESS)
                .setCreatedByUserId(101);
        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
        List<Long> createdByUserIds = orders.stream()
                .map(Order::getCreatedByUserId)
                .collect(Collectors.toList());

        assertThat(createdByUserIds.get(0)).isEqualTo(101);
        assertThat(createdByUserIds.get(1)).isEqualTo(103);
        assertThat(createdByUserIds.get(2)).isEqualTo(102);
        assertThat(createdByUserIds.get(3)).isEqualTo(104);
        assertThat(createdByUserIds.get(4)).isEqualTo(101);
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

        // how to do
        List<String> emails = new ArrayList<>();
        for (User user : users) {
            if (!user.isVerified()) {
                String email = user.getEmailAddress();
                emails.add(email);
            }
        }

        // what to do
        List<String> emails2 = users.stream()
                .filter(user -> !user.isVerified())
                .map(User::getEmailAddress)
                .collect(Collectors.toList());

        assertThat(emails).isEqualTo(emails2);
    }

    @Test
    void test4() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Order order1 = new Order()
                .setId(1001)
                .setStatus(Order.OrderStatus.CREATED)
                .setCreatedByUserId(101)
                .setCreatedAt(now.minusHours(4));
        Order order2 = new Order()
                .setId(1002)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(103)
                .setCreatedAt(now.minusHours(1));
        Order order3 = new Order()
                .setId(1003)
                .setStatus(Order.OrderStatus.PROCESSED)
                .setCreatedByUserId(102)
                .setCreatedAt(now.minusHours(36));
        Order order4 = new Order()
                .setId(1004)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(104)
                .setCreatedAt(now.minusHours(15));
        Order order5 = new Order()
                .setId(1005)
                .setStatus(Order.OrderStatus.IN_PROGRESS)
                .setCreatedByUserId(101)
                .setCreatedAt(now.minusHours(10));
        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
        List<Long> createdByUserIds = orders.stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.ERROR)
                .map(Order::getCreatedByUserId)
                .collect(Collectors.toList());

        assertThat(createdByUserIds.get(0)).isEqualTo(103);
        assertThat(createdByUserIds.get(1)).isEqualTo(104);

        List<Order> ordersInErrorStatusIn24hrs = orders.stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.ERROR)
                .filter(order -> order.getCreatedAt().isAfter(now.minusHours(24)))
                .collect(Collectors.toList());

        assertThat(ordersInErrorStatusIn24hrs.get(0).getId()).isEqualTo(1002);
        assertThat(ordersInErrorStatusIn24hrs.get(1).getId()).isEqualTo(1004);
    }

    @Test
    void sortTest() {
        List<Integer> numbers = Arrays.asList(3, -5, 7, 4);
        List<Integer> sortedNumbers = numbers.stream()
                .sorted()
                .collect(Collectors.toList());

        assertThat(sortedNumbers.get(0)).isEqualTo(-5);
        assertThat(sortedNumbers.get(1)).isEqualTo(3);
        assertThat(sortedNumbers.get(2)).isEqualTo(4);
        assertThat(sortedNumbers.get(3)).isEqualTo(7);
    }

    @Test
    void sortTest2() {
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
        List<User> sortedUsers = users.stream()
                .sorted((u1, u2) -> u1.getName().compareTo(u2.getName()))
                .collect(Collectors.toList());

        assertThat(sortedUsers.get(0).getName()).isEqualTo("Apple");
        assertThat(sortedUsers.get(1).getName()).isEqualTo("Banana");
        assertThat(sortedUsers.get(2).getName()).isEqualTo("Kiwi");
    }

    @Test
    void sortTest3() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Order order1 = new Order()
                .setId(1001)
                .setStatus(Order.OrderStatus.CREATED)
                .setCreatedByUserId(101)
                .setCreatedAt(now.minusHours(4));
        Order order2 = new Order()
                .setId(1002)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(103)
                .setCreatedAt(now.minusHours(1));
        Order order3 = new Order()
                .setId(1003)
                .setStatus(Order.OrderStatus.PROCESSED)
                .setCreatedByUserId(102)
                .setCreatedAt(now.minusHours(36));
        Order order4 = new Order()
                .setId(1004)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(104)
                .setCreatedAt(now.minusHours(15));
        Order order5 = new Order()
                .setId(1005)
                .setStatus(Order.OrderStatus.IN_PROGRESS)
                .setCreatedByUserId(101)
                .setCreatedAt(now.minusHours(10));
        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);
        List<Order> sortedOrders = orders.stream()
                .sorted((o1, o2) -> o1.getCreatedAt().compareTo(o2.getCreatedAt()))
                .collect(Collectors.toList());

        assertThat(sortedOrders.get(0).getId()).isEqualTo(1003);
        assertThat(sortedOrders.get(1).getId()).isEqualTo(1004);
        assertThat(sortedOrders.get(2).getId()).isEqualTo(1005);
        assertThat(sortedOrders.get(3).getId()).isEqualTo(1001);
        assertThat(sortedOrders.get(4).getId()).isEqualTo(1002);
    }

    @Test
    void distinctTest1() {
        List<Integer> numbers = Arrays.asList(3, -5, 4, -5, 2, 3);
        List<Integer> distinctNumbers = numbers.stream()
                .distinct()
                .collect(Collectors.toList());

        assertThat(distinctNumbers.size()).isEqualTo(4);
    }

    @Test
    void distinctTest2() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Order order1 = new Order()
                .setId(1001)
                .setStatus(Order.OrderStatus.CREATED)
                .setCreatedByUserId(101)
                .setCreatedAt(now.minusHours(4));
        Order order2 = new Order()
                .setId(1002)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(103)
                .setCreatedAt(now.minusHours(1));
        Order order3 = new Order()
                .setId(1003)
                .setStatus(Order.OrderStatus.PROCESSED)
                .setCreatedByUserId(102)
                .setCreatedAt(now.minusHours(36));
        Order order4 = new Order()
                .setId(1004)
                .setStatus(Order.OrderStatus.ERROR)
                .setCreatedByUserId(104)
                .setCreatedAt(now.minusHours(15));
        Order order5 = new Order()
                .setId(1005)
                .setStatus(Order.OrderStatus.IN_PROGRESS)
                .setCreatedByUserId(101)
                .setCreatedAt(now.minusHours(10));
        List<Order> orders = Arrays.asList(order1, order2, order3, order4, order5);

        List<Long> distinctOrders = orders.stream()
                .map(Order::getCreatedByUserId)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        assertThat(distinctOrders.get(0)).isEqualTo(101);
        assertThat(distinctOrders.get(1)).isEqualTo(102);
        assertThat(distinctOrders.get(2)).isEqualTo(103);
        assertThat(distinctOrders.get(3)).isEqualTo(104);
    }

    @Test
    void flatMapTest1() {
        String[][] cities = new String[][]{
                {"Seoul", "Busan"},
                {"San Francisco", "New York"},
                {"Madrid", "Barcelona"}
        };
        Stream<String[]> cityStream = Arrays.stream(cities);
        Stream<Stream<String>> cityStreamStream = cityStream.map(x -> Arrays.stream(x));
        List<Stream<String>> cityStreamList = cityStreamStream.collect(Collectors.toList());

        Stream<String[]> cityStream2 = Arrays.stream(cities);
        Stream<String> flattenedCityStream = cityStream2.flatMap(x -> Arrays.stream(x));
        List<String> flattenedCityList = flattenedCityStream.collect(Collectors.toList());

        assertThat(flattenedCityList.get(0)).isEqualTo("Seoul");
        assertThat(flattenedCityList.get(1)).isEqualTo("Busan");
        assertThat(flattenedCityList.get(2)).isEqualTo("San Francisco");
        assertThat(flattenedCityList.get(3)).isEqualTo("New York");
        assertThat(flattenedCityList.get(4)).isEqualTo("Madrid");
        assertThat(flattenedCityList.get(5)).isEqualTo("Barcelona");
    }

    @Test
    void flatMapTest2() {
        Order order1 = new Order()
                .setId(1001)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setId(10001)
                                .setType(OrderLine.OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(5000)),
                        new OrderLine()
                                .setId(10002)
                                .setType(OrderLine.OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(4000))
                ));
        Order order2 = new Order()
                .setId(1002)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setId(10003)
                                .setType(OrderLine.OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(2000)),
                        new OrderLine()
                                .setId(10004)
                                .setType(OrderLine.OrderLineType.DISCOUNT)
                                .setAmount(BigDecimal.valueOf(-1000))
                ));
        Order order3 = new Order()
                .setId(1003)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setId(10005)
                                .setType(OrderLine.OrderLineType.PURCHASE)
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        List<Order> orders = Arrays.asList(order1, order2, order3);
        List<OrderLine> mergeOrderLines = orders.stream() // Stream<Order>
                .map(Order::getOrderLines)                  // Stream<List<OrderLine>>
                .flatMap(List::stream)                      // Stream<OrderLine>
                .collect(Collectors.toList());

        assertThat(mergeOrderLines.get(0).getType()).isEqualTo(OrderLine.OrderLineType.PURCHASE);
        assertThat(mergeOrderLines.get(1).getType()).isEqualTo(OrderLine.OrderLineType.PURCHASE);
        assertThat(mergeOrderLines.get(2).getType()).isEqualTo(OrderLine.OrderLineType.PURCHASE);
        assertThat(mergeOrderLines.get(3).getType()).isEqualTo(OrderLine.OrderLineType.DISCOUNT);
        assertThat(mergeOrderLines.get(4).getType()).isEqualTo(OrderLine.OrderLineType.PURCHASE);
    }
}
