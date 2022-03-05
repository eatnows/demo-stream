package me.eatnows.streamdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FindTest {

    @Test
    @DisplayName("숫자중 음수인 숫자 아무거나 꺼내오기")
    void test1() {
        Optional<Integer> anyNegativeInteger = Stream.of(3, 2, -5, 6)
                .filter(x -> x < 0)
                .findAny();

        assertThat(anyNegativeInteger.get()).isEqualTo(-5);
    }

    @Test
    @DisplayName("숫자중 첫번째 양수를 꺼내오기")
    void test2() {
        Optional<Integer> firstPositiveInteger = Stream.of(3, 2, -5, 4)
                .filter(n -> n > 0)
                .findFirst();

        assertThat(firstPositiveInteger.get()).isEqualTo(3);
    }

}