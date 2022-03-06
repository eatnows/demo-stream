package me.eatnows.streamdemo;

import me.eatnows.streamdemo.filter.Order;
import me.eatnows.streamdemo.filter.OrderLine;
import me.eatnows.streamdemo.maxmincount.User;
import org.assertj.core.internal.Numbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReduceTest {

    @Test
    void test1() {
        List<Integer> numbers = Arrays.asList(1, 4, -2, -5, 3);
        int sum = numbers.stream()
                .reduce((x, y) -> x + y)
                .get();

        assertThat(sum).isEqualTo(1);
    }

    @Test
    @DisplayName("Stream.min을 reduce로 구현해보기")
    void test2() {
        List<Integer> numbers = Arrays.asList(1, 4, -2, -5, 3);
        int min1 = numbers.stream()
                .reduce((x, y) -> {
                    if (x < y) {
                        return x;
                    }
                    return y;
                })
                .get();

        assertThat(min1).isEqualTo(-5);

        int min2 = numbers.stream()
                .reduce((x, y) -> x < y ? x : y)
                .get();
        assertThat(min2).isEqualTo(-5);
    }

    @Test
    @DisplayName("초기값(identity)을 제공하는 Reduce")
    void test3() {
        List<Integer> numbers = Arrays.asList(1, 4, -2, -5, 3);
        // 초기값때문에 무조건 값이 있기 때문에 Optional이 아닌 값을 바로 반환해줌
        int product = numbers.stream()
                .reduce(1, (x, y) -> x * y);

        assertThat(product).isEqualTo(120);
    }

    @Test
    void test4() {
        List<String> numberStrList = Arrays.asList("3", "2", "5", "-4");
        int sumOfNumberStrList = numberStrList.stream()
                .map(Integer::parseInt)
                .reduce(0, (x, y) -> x + y);

        assertThat(sumOfNumberStrList).isEqualTo(6);

        // map과 reduce를 사용한것을 reduce로 한 번에 할 수 있다. 자주 사용하지는 않는다.
        int sumOfNumberStrList2 = numberStrList.stream()
                .reduce(0, (number, str) -> number + Integer.parseInt(str), (num1, num2) -> num1 + num2);
        assertThat(sumOfNumberStrList2).isEqualTo(6);
    }

    @Test
    void test5() {
        User user1 = new User()
                .setId(101)
                .setName("Apple")
                .setFriendUserIds(Arrays.asList(201, 202, 203, 204));
        User user2 = new User()
                .setId(102)
                .setName("Kiwi")
                .setFriendUserIds(Arrays.asList(204, 205, 206));
        User user3 = new User()
                .setId(103)
                .setName("Banana")
                .setFriendUserIds(Arrays.asList(204, 205, 207));
        List<User> users = Arrays.asList(user1, user2, user3);

        int sumOfNumberOfFriends = users.stream()
                .map(User::getFriendUserIds)
                .map(List::size)
                .reduce(0, (x, y) -> x + y);

        assertThat(sumOfNumberOfFriends).isEqualTo(10);
    }

    @Test
    void test6() {
        Order order1 = new Order()
                .setId(1001)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));
        Order order2 = new Order()
                .setId(1002)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(3000))
                ));
        Order order3 = new Order()
                .setId(1003)
                .setOrderLines(Arrays.asList(
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine()
                                .setAmount(BigDecimal.valueOf(2000))
                ));

        List<Order> orders = Arrays.asList(order1, order2, order3);

        BigDecimal sumAmountOfOrderLine1 = orders.stream()
                .map(Order::getOrderLines)
                .flatMap(orderLines -> orderLines.stream().map(OrderLine::getAmount))
                .reduce(BigDecimal.valueOf(0), (x, y) -> x.add(y));
        assertThat(sumAmountOfOrderLine1).isEqualTo(BigDecimal.valueOf(11000));


        BigDecimal sumAmountOfOrderLine2 = orders.stream()
                .map(Order::getOrderLines)          // Stream<List<OrderLine>>
                .flatMap(List::stream)  // Stream<OrderLine>
                .map(OrderLine::getAmount)  // Stream<BigDecimal>
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertThat(sumAmountOfOrderLine2).isEqualTo(BigDecimal.valueOf(11000));
    }
}
