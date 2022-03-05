package me.eatnows.streamdemo;

import me.eatnows.streamdemo.optional.User;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OptionalTest {

    @Test
    void test1() {
        User user1 = new User()
                .setId(1001)
                .setName("Apple")
                .setVerified(false);
        User user2 = new User()
                .setId(1001)
                .setName("Apple")
                .setEmailAddress("apple@email.com")
                .setVerified(false);

        assertThatThrownBy(() -> userEquals(user1, user2))
                .isInstanceOf(NullPointerException.class);
    }

    public boolean userEquals(User u1, User u2) {
        return u1.getId() == u2.getId()
                && u1.getName().equals(u2.getName())
                && u1.getEmailAddress().equals(u2.getEmailAddress())
                && u1.isVerified() == u2.isVerified();
    }

    @Test
    void test2() {
        String someEmail = "some@email.com";
        String nullEmail = null;

        Optional<String> maybeEmail1 = Optional.of(someEmail);
        Optional<String> maybeEmail2 = Optional.empty();
        Optional<String> maybeEmail3 = Optional.ofNullable(someEmail);
        Optional<String> maybeEmail4 = Optional.ofNullable(nullEmail);

        String email = maybeEmail1.get();
        assertThat(email).isEqualTo(maybeEmail1.get());
        assertThatThrownBy(() -> maybeEmail2.get()).isInstanceOf(NoSuchElementException.class);

        if (maybeEmail2.isPresent()) {
            System.out.println(maybeEmail2.get());
        }

        String defaultEmail = "default@email.com";
        String email3 = maybeEmail2.orElse(defaultEmail);
        assertThat(email3).isEqualTo(defaultEmail);

        String email4 = maybeEmail2.orElseGet(() -> defaultEmail);
        assertThat(email4).isEqualTo(defaultEmail);


        assertThatThrownBy(() -> maybeEmail2.orElseThrow(() -> new RuntimeException("이메일이 없습니다.")))
                .isInstanceOf(RuntimeException.class);

    }

    public User maybeGetUser(boolean returnUser) {
        if (returnUser) {
            return new User()
                    .setId(1001)
                    .setName("Apple")
                    .setEmailAddress("apple@email.com")
                    .setVerified(false);
        }
        return null;
    }

    @Test
    void test3() {
        Optional<User> maybeUser = Optional.ofNullable(maybeGetUser(false));
        maybeUser.ifPresent(System.out::println);

        Optional<Integer> maybeId = Optional.ofNullable(maybeGetUser(true))
                .map(User::getId);
        maybeId.ifPresent(System.out::println);

        String userName = Optional.ofNullable(maybeGetUser(true))
                .map(User::getName)
                .map(name -> "The name is " + name)
                .orElse("Name is Empty");

        System.out.println(userName);

        Optional<Optional<String>> maybeEmail1 = Optional.ofNullable(maybeGetUser(false))
                .map(User::getEmailAddress);

        Optional<String> maybeEmail2 = Optional.ofNullable(maybeGetUser(true))
                .flatMap(User::getEmailAddress);
        maybeEmail2.ifPresent(System.out::println);
    }
}
