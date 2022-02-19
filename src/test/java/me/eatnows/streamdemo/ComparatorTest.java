package me.eatnows.streamdemo;

import me.eatnows.streamdemo.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ComparatorTest {

    @Test
    void test1() {
        List<User> users = new ArrayList<>();
        users.add(new User(3, "Kiwi"));
        users.add(new User(1, "Apple"));
        users.add(new User(5, "Banana"));

        assertThat(users.get(0).getId()).isEqualTo(3);
        assertThat(users.get(1).getId()).isEqualTo(1);
        assertThat(users.get(2).getId()).isEqualTo(5);

        Comparator<User> idComparator = (u1, u2) -> u1.getId() - u2.getId();
        Collections.sort(users, idComparator);

        assertThat(users.get(0).getId()).isEqualTo(1);
        assertThat(users.get(1).getId()).isEqualTo(3);
        assertThat(users.get(2).getId()).isEqualTo(5);

        Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName()));

        assertThat(users.get(0).getId()).isEqualTo(1);
        assertThat(users.get(1).getId()).isEqualTo(5);
        assertThat(users.get(2).getId()).isEqualTo(3);
    }
}
