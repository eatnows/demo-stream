package me.eatnows.streamdemo;

import me.eatnows.streamdemo.util.Adder;
import me.eatnows.streamdemo.util.TriFunction;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class LambdaTest {

    @Test
    void adderTest1() {
        Function<Integer, Integer> adder = new Adder();

        assertThat(adder.apply(10)).isEqualTo(20);
        assertThat(adder.apply(0)).isEqualTo(10);
    }

    @Test
    void adderTest2() {
        // 매개변의 타입이 유추 가능할 경우 타입 생략 가능
        // 매개변수가 하나일 경우 괄호 생략 가능
        // 바로 리턴하는 경우 중괄호 생략 가능
        Function<Integer, Integer> adder = (Integer x) -> {
            return x + 10;
        };

        assertThat(adder.apply(10)).isEqualTo(20);
        assertThat(adder.apply(0)).isEqualTo(10);
    }

    @Test
    void adderTest3() {
        Function<Integer, Integer> adder = x -> x + 10;

        assertThat(adder.apply(10)).isEqualTo(20);
        assertThat(adder.apply(0)).isEqualTo(10);
    }

    @Test
    void biFunctionTest1() {
        // 매개변수가 2개인 function interface
        BiFunction<Integer, Integer, Integer> add = (Integer x, Integer y) -> {
            return x + y;
        };

        assertThat(add.apply(10, 15)).isEqualTo(25);
        assertThat(add.apply(0, 3)).isEqualTo(3);
    }

    @Test
    void biFunctionTest2() {
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;

        assertThat(add.apply(10, 15)).isEqualTo(25);
        assertThat(add.apply(0, 3)).isEqualTo(3);
    }

    @Test
    void TriFunctionTest1() {
        TriFunction<Integer, Integer, Integer, Integer> add = (x, y, z) -> x + y + z;

        assertThat(add.apply(10, 15, 0)).isEqualTo(25);
        assertThat(add.apply(1, 3, 206)).isEqualTo(210);
    }
}
