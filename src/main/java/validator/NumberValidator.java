package validator;

import java.util.Arrays;

public class NumberValidator {
    public static final String ERROR_MESSAGE = "Invalid number";

    private NumberValidator() {
    }

    public static boolean isValid(String... numbers) {
        return Arrays.stream(numbers).allMatch(number -> number.matches("^\\d+$"));
    }
}
