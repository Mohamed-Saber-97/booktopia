package org.example.booktopia.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
    private String message;
    private HttpStatus statusCode;

    public ErrorMessage(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
