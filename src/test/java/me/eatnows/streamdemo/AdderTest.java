package me.eatnows.streamdemo;

import me.eatnows.streamdemo.util.Adder;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class AdderTest {

    @Test
    void adderTest() {
        Function<Integer, Integer> adder = new Adder();

        assertThat(adder.apply(10)).isEqualTo(20);
        assertThat(adder.apply(0)).isEqualTo(10);
    }
}
