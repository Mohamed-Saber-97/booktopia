package org.example.booktopia.error;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String... message) {
        super("This record with %s:- %s not found".formatted(message[0], message[1]));
    }

}
