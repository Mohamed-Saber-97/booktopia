package validator;

import java.util.Arrays;

public class MaxFieldLengthValidator {
    private static int length = 100;
    public static final String ERROR_MESSAGE = "Name must be up to %d characters".formatted(length);

    private MaxFieldLengthValidator() {
    }

    public static boolean isValid(int length, String... fields) {
        MaxFieldLengthValidator.length = length;
        return Arrays.stream(fields).allMatch(field -> field.length() <= length);
    }
}