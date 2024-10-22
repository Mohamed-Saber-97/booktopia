package org.example.booktopia.error;

public class IllegalValueException extends RuntimeException {

    public IllegalValueException(String fieldName, String invalidValue) {
        super("%s cannot be set to %s".formatted(fieldName, invalidValue));
    }
}