package me.eatnows.streamdemo;

import me.eatnows.streamdemo.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodReferenceTest {

    @Test
    @DisplayName("클래스의 static method 참조")
    void test1() {
        Function<String, Integer> strInt = Integer::parseInt;

        assertThat(strInt.apply("20")).isEqualTo(20);
    }

    @Test
    @DisplayName("이미 선언된 객체의 method 참조")
    void test2() {
        String str = "hello";
        Predicate<String> equalsToHello = str::equals;

        assertThat(equalsToHello.test("hello")).isEqualTo(true);
        assertThat(equalsToHello.test("world")).isEqualTo(false);
    }

    public static int calculate(int x, int y, BiFunction<Integer, Integer, Integer> operator) {
        return operator.apply(x, y);
    }

    public static int multiply(int x, int y) {
        return x * y;
    }

    public int subtract(int x, int y) {
        return x - y;
    }

    @Test
    void test3() {
        assertThat(calculate(8, 2, (x, y) -> x + y)).isEqualTo(10);
        assertThat(calculate(8, 2, MethodReferenceTest::multiply)).isEqualTo(16);
        assertThat(calculate(8, 2, this::subtract)).isEqualTo(6);
    }

    @Test
    void test4() {
        Function<String, Integer> strLength = String::length;
        assertThat(strLength.apply("hello world")).isEqualTo(11);

        BiPredicate<String, String> strEquals = String::equals;
        assertThat(strEquals.test("hello", "world")).isEqualTo(false);
        assertThat(strEquals.test("hello", "hello")).isEqualTo(true);
        assertThat(strEquals.test("hello", "HELLO")).isEqualTo(false);
    }

    public static void printUserField(List<User> users, Function<User, Object> getter) {
        for (User user : users) {
            System.out.println(getter.apply(user));
        }
    }

    @Test
    void test5() {
        List<User> users = new ArrayList<>();
        users.add(new User(3, "Kiwi"));
        users.add(new User(1, "Apple"));
        users.add(new User(5, "Banana"));

        printUserField(users, User::getName);
    }

    @Test
    void test6() {
        User user = new User(1, "Apple");

        BiFunction<Integer, String, User> userCreator = User::new;
        User kiwi = userCreator.apply(3, "Kiwi");
        assertThat(kiwi.getId()).isEqualTo(3);
    }

    @Test
    void test7() {
        Map<String, BiFunction<String, String, Car>> carTypeToConstructorMap = new HashMap<>();
        carTypeToConstructorMap.put("sedan", Sedan::new);
        carTypeToConstructorMap.put("suv", Suv::new);
        carTypeToConstructorMap.put("van", Van::new);

        String[][] inputs = new String[][] {
                {"sedan", "Sonata", "Hyundai"},
                {"van", "Sienna", "Toyota"},
                {"sedan", "Model S", "Tesla"},
                {"suv", "Sorento", "KIA"}
        };

        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            String[] input = inputs[i];
            String carType = input[0];
            String name = input[1];
            String brand = input[2];

            cars.add(carTypeToConstructorMap.get(carType).apply(name, brand));
        }

        for (Car car : cars) {
            car.drive();
        }
    }
}
