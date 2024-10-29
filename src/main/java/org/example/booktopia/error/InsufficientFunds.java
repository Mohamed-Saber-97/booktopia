package org.example.booktopia.error;

public class InsufficientFunds extends Exception {
    public InsufficientFunds() {
        super("Transaction declined insufficient funds");
    }
}
