package me.eatnows.streamdemo.tdd.pwd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordMeter {

    public PasswordStrength meter(String password) {
        if (password == null) {
            return PasswordStrength.INVALID;
        }
        if (password.isEmpty()) {
            return PasswordStrength.INVALID;
        }

        int metCount = 0;
        if (password.length() >= 8) metCount++;
        if (containsDigit(password)) metCount++;
        if (containsUppercase(password)) metCount++;
        if (metCount == 0) {
            return PasswordStrength.WEAK;
        }
        if (metCount == 1) {
            return PasswordStrength.WEAK;
        }

        if (metCount == 2) {
            return PasswordStrength.NORMAL;
        }

        return PasswordStrength.STRONG;
    }

    private boolean containsDigit(String password) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    private boolean containsUppercase(String password) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
