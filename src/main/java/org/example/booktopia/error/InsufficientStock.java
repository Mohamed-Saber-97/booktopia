package org.example.booktopia.error;

public class InsufficientStock extends Exception {
    public InsufficientStock() {
        super("Transaction declined insufficient stock");
    }
}
