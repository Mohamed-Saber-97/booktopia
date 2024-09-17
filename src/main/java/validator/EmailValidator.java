package validator;

import java.util.Arrays;

public class EmailValidator {
    public static final String ERROR_MESSAGE = "Invalid email";

    private EmailValidator() {
    }

    public static boolean isValid(String... emails) {
        return Arrays.stream(emails).allMatch(email -> email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"));
    }
}