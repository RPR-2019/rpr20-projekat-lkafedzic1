package ba.unsa.etf.rpr.project.controller;

import java.util.regex.Pattern;

public interface Validation {
    default boolean isValidName(String s) {
        return s.length() >= 2 && s.chars()
                .allMatch(Character::isLetter);
    }
    default boolean isValidUsername (String s) {
        return s.length() >= 2 && s.chars()
                .allMatch(Character::isLetterOrDigit) && isValidStart(s);
    }
    private boolean isValidStart (String s) {
        //username has to begin with letter
        return !Character.isDigit(s.charAt(0));
    }
    default boolean isValidEmail(String s) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        if (s == null) return false;
        return pattern.matcher(s).matches();
    }
    default boolean isValidPassword (String s) {
        return (s.trim().isEmpty() || s.trim().length() < 8);
    }
}
