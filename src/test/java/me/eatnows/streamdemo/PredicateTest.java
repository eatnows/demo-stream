package me.eatnows.streamdemo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class PredicateTest {

    @Test
    void predicateTest1() {
        Predicate<Integer> isPositive = x -> x > 0;

        assertThat(isPositive.test(10)).isEqualTo(true);
        assertThat(isPositive.test(0)).isEqualTo(false);
        assertThat(isPositive.test(-1)).isEqualTo(false);
    }

    public static <T> List<T> filter(List<T> inputs, Predicate<T> condition) {
        List<T> outputs = new ArrayList<>();
        for (T input : inputs) {
            if (condition.test(input)) {
                outputs.add(input);
            }
        }

        return outputs;
    }

    @Test
    void predicateTest2() {
        Predicate<Integer> isPositive = x -> x > 0;

        List<Integer> inputs = Arrays.asList(10, -5, 4, -2, 0);
        assertThat(filter(inputs, isPositive)).isEqualTo(Arrays.asList(10, 4));
    }

    @Test
    void predicateTest3() {
        Predicate<Integer> isPositive = x -> x > 0;

        List<Integer> inputs = Arrays.asList(10, -5, 4, -2, 0);
        assertThat(filter(inputs, isPositive.negate())).isEqualTo(Arrays.asList(-5, -2, 0));
    }

    @Test
    void predicateTest4() {
        Predicate<Integer> isPositive = x -> x > 0;

        List<Integer> inputs = Arrays.asList(10, -5, 4, -2, 0);
        assertThat(filter(inputs, isPositive.or(isPositive.negate()))).isEqualTo(Arrays.asList(10, -5, 4, -2, 0));
    }

    @Test
    void predicateTest5() {
        Predicate<Integer> isPositive = x -> x > 0;

        List<Integer> inputs = Arrays.asList(10, -5, 4, -2, 0);
        assertThat(filter(inputs, isPositive.or(x -> x == 0))).isEqualTo(Arrays.asList(10, 4, 0));
    }

    @Test
    void predicateTest6() {
        Predicate<Integer> isPositive = x -> x > 0;

        List<Integer> inputs = Arrays.asList(10, -5, 4, -2, 0, 3);

        assertThat(filter(inputs, isPositive.and(x -> x % 2 == 0))).isEqualTo(Arrays.asList(10, 4));
    }
}
