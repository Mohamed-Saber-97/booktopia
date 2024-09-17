package validator;

import java.util.Arrays;

public class NotEmptyValidator {
    public static final String ERROR_MESSAGE = "All fields are required";

    private NotEmptyValidator() {
    }

    public static boolean isValid(String... values) {
        return Arrays.stream(values).allMatch(value -> value != null && !value.isEmpty());
    }
}
