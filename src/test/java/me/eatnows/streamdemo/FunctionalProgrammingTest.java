package me.eatnows.streamdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class FunctionalProgrammingTest {

    @Test
    void test1() {
        // 외부함수에 있는 hello가 사라지지 않는다.
        Supplier<String> supplier = getStringSupplier();

        assertThat(supplier.get()).isEqualTo("HelloWorld");
    }

    public Supplier<String> getStringSupplier() {
        String hello = "Hello";
        Supplier<String> supplier = () -> {
            String world = "World";
            return hello + world;
        };

        return supplier;
    }

    @Test
    @DisplayName("curry")
    void test2() {
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        // 위에 코드를 아래와 같이 사용할 수 있다.
        Function<Integer, Function<Integer, Integer>>  curriedAdd = x -> y -> x + y;

        Function<Integer, Integer> addThree = curriedAdd.apply(3);
        int result = addThree.apply(10);

        assertThat(result).isEqualTo(13);
    }
}
