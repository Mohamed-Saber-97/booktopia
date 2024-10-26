package org.example.booktopia.validator;

import java.util.Arrays;

public class MinFieldLengthValidator {
    private static int length = 6;
    public static final String ERROR_MESSAGE = "Password must be at least %d characters long".formatted(length);

    private MinFieldLengthValidator() {
    }

    public static boolean isValid(int length, String... fields) {
        MinFieldLengthValidator.length = length;
        return Arrays.stream(fields).allMatch(field -> field.length() >= length);
    }
}
