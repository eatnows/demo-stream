package me.eatnows.streamdemo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionalTest {

    @Test
    void supplierTest() {
        // supplier 파라미터는 없고 무언가 반환만 하는 타입
        String message = "hello world";

        Supplier<String> myStringSupplier = () -> message;

        assertThat(myStringSupplier.get()).isEqualTo(message);
    }

    @Test
    void supplierTest2() {
        Supplier<Double> myRandomDoubleSupplier = () -> Math.random();

        System.out.println(myRandomDoubleSupplier.get());
        System.out.println(myRandomDoubleSupplier.get());
        System.out.println(myRandomDoubleSupplier.get());
        System.out.println(myRandomDoubleSupplier.get());
    }

    public static void printRandomDoubles(Supplier<Double> randomSupplier, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(randomSupplier.get());
        }
    }

    @Test
    void supplierTest3() {
        Supplier<Double> myRandomDoubleSupplier = () -> Math.random();
        printRandomDoubles(myRandomDoubleSupplier, 5);
    }

    @Test
    void consumerTest1() {
        Consumer<String> myStringConsumer = (String str) -> {
            System.out.println(str);
        };

        myStringConsumer.accept("Hello worlds~");
        myStringConsumer.accept("hi hello");
    }

    public static <T> void process(List<T> inputs, Consumer<T> processor) {
        for (T input : inputs) {
            processor.accept(input);
        }
    }

    @Test
    void consumerTest2() {
        List<Integer> integerInputs = Arrays.asList(4, 2, 3);
        // return 타입이 아니여도 consumer의 경우 생략 가능
        Consumer<Integer> myIntegerProcessor = integer -> System.out.println("Processing integer " + integer);

        process(integerInputs, myIntegerProcessor);

        Consumer<Integer> myDifferentIntegerProcessor = i -> System.out.println("Processing integer in different way " + i);
        process(integerInputs, myDifferentIntegerProcessor);
    }

    @Test
    void consumerTest3() {
        List<Double> doubleInputs = Arrays.asList(3.2, 2.2, 3.3);
        Consumer<Double> myDoubleProcessor = d -> System.out.println("Processing double " + d);

        process(doubleInputs, myDoubleProcessor);
    }

    @Test
    void biConsumerTest1() {
        BiConsumer<Integer, Double> myDoubleProcessor = (index, input) -> System.out.println("Processing " + input + " at index " + index);

        List<Double> inputs = Arrays.asList(1.1, 2.2, 3.3);
        process(inputs, myDoubleProcessor);
    }

    public static <T> void process(List<T> inputs, BiConsumer<Integer, T> processor) {
        for (int i = 0; i < inputs.size(); i++) {
            processor.accept(i, inputs.get(i));
        }
    }
}
