package org.example.booktopia.error;

public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException(String fieldName, String duplicateValue) {
        super("A record with %s: %s already exists".formatted(fieldName, duplicateValue));
    }
}
