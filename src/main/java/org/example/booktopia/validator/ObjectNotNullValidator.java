package org.example.booktopia.validator;

import java.util.Arrays;
import java.util.Objects;

public class ObjectNotNullValidator {
    public static final String ERROR_MESSAGE = "Object cannot be null";

    private ObjectNotNullValidator() {
    }

    public static boolean isValid(Object... objects) {
        return Arrays.stream(objects).noneMatch(Objects::isNull);
    }
}
