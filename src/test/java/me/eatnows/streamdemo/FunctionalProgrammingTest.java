package me.eatnows.streamdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    @DisplayName("Lazy Evaluation 개념")
    void test3() {
        if (returnTrue() || returnFalse()) {
            System.out.println("true");
        }

        // or 메서드 호출전에 매개변수의 값을 모두 알아야하기 때문에 returnTrue, returnFalse 메서드를 모두 실행한다.
        if (or(returnTrue(), returnFalse())) {
            System.out.println("true");
        }

        // () -> returnTrue()가 true를 반환하기 때문에 () -> returnFalse() 는 실행되지 않는다.
        if (lazyOr(() -> returnTrue(), () -> returnFalse())) {
            System.out.println("true");
        }
    }

    @Test
    @DisplayName("Stream의 Lazy Evaluation")
    void test3_2() {
        // Stream은 종결처리가 이루어질때까지 모든 계산을 미룬다. integerStream.collect(Collectors.toList())가 되야 계산을 한다.
        Stream<Integer> integerStream = Stream.of(3, -2, 5, 8, -3, 10)
                .filter(x -> x > 0)
                .peek(x -> System.out.println("peeking: " + x))
                .filter(x -> x % 2 == 0);
        System.out.println("Before collect");

        List<Integer> integers = integerStream.collect(Collectors.toList());
        System.out.println("After collect: " + integers);
    }

    public boolean or (boolean x, boolean y) {
        return x || y;
    }

    public boolean lazyOr(Supplier<Boolean> x, Supplier<Boolean> y) {
        return x.get() || y.get();
    }

    public boolean returnTrue() {
        System.out.println("Returning true");
        return true;
    }

    public boolean returnFalse() {
        System.out.println("Returning false");
        return false;
    }
}
