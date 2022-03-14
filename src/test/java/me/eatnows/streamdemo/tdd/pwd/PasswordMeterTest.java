package me.eatnows.streamdemo.tdd.pwd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordMeterTest {

    @Test
    void nullInput() {
        assertPasswordStrength(
                null,
                PasswordStrength.INVALID);
    }

    private void assertPasswordStrength(String password, PasswordStrength expected) {
        PasswordMeter meter = new PasswordMeter();
        PasswordStrength result = meter.meter(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void emptyInput() {
        assertPasswordStrength("", PasswordStrength.INVALID);
    }

    @Test
    void meetAllRules() {
        assertPasswordStrength(
                "abcdABCD123",
                PasswordStrength.STRONG);
        assertPasswordStrength(
                "123abcdABCD",
                PasswordStrength.STRONG);
        assertPasswordStrength(
                "ABCD123abcd",
                PasswordStrength.STRONG);
    }

    @Test
    void meet2RulesExceptForLengthRule() {
        assertPasswordStrength(
                "abc12AB",
                PasswordStrength.NORMAL);
        assertPasswordStrength(
                "12ABabc",
                PasswordStrength.NORMAL);
    }

    @Test
    void meet2RulesExceptForDigitRule() {
        assertPasswordStrength(
                "abcdABabc",
                PasswordStrength.NORMAL);
        assertPasswordStrength(
                "asdvzzwpA",
                PasswordStrength.NORMAL);
    }

    @Test
    void meet2RulesExceptForUppercaseRule() {
        assertPasswordStrength(
                "abcde1234",
                PasswordStrength.NORMAL);
    }

    @Test
    void meetOnlyLengthRule() {
        assertPasswordStrength(
                "asdasflbeewer",
                PasswordStrength.WEAK);
    }

    @Test
    void meetOnlyDigitRule() {
        assertPasswordStrength(
                "abcd123",
                PasswordStrength.WEAK);
    }

    @Test
    void meetOnlyUppercaseRule() {
        assertPasswordStrength(
                "abcABC",
                PasswordStrength.WEAK);
    }

    @Test
    void noRules() {
        assertPasswordStrength(
                "asbde",
                PasswordStrength.WEAK);
    }
}
