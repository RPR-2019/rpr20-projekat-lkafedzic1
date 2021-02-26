package ba.unsa.etf.rpr.project;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validation {

    default boolean isValidName(String s) {
        Pattern pattern = Pattern.compile(new String ("^[\\p{L} .'-]+$")); //allow letters from any language, space...
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
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

    default boolean isValidTitle (String s) {
        return s.matches("[a-zA-Z .]+$");
    }
}
