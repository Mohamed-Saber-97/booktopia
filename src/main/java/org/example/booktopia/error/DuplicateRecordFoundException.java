package org.example.booktopia.error;

public class DuplicateRecordFoundException extends RuntimeException {
    public DuplicateRecordFoundException() {
        super();
    }

    public DuplicateRecordFoundException(String... message) {
        super("This record with %s:- %s already exists".formatted(message[0], message[1]));
    }
}
