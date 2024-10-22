package org.example.booktopia.error;

public class IllegalQuantityException extends RuntimeException {
    public IllegalQuantityException() {
        super();
    }

    public IllegalQuantityException(String... message) {
        super("Quantity cannot be set to %s".formatted(message[0]));
    }
}
