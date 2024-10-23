package org.example.booktopia.error;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super("Transaction declined insufficient funds");
    }
}
