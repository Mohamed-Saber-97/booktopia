package org.example.booktopia.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private Boolean success;
    private String message;
    private LocalDateTime dateTime;
    private List<String> details;

    public ErrorResponse() {
        super();
    }

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
        this.success = Boolean.FALSE;
        this.dateTime = LocalDateTime.now();
    }
}
