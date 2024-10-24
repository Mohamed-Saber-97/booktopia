package org.example.booktopia.error;

public class InvalidLoginCredentialsException extends RuntimeException {
    public InvalidLoginCredentialsException() {
        super("Invalid Login Credentials");
    }
}
