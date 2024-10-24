package org.example.booktopia.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class Result<T> {
    private Boolean success;
    private HttpStatus status;
    private String message;
    private T data;

    public static <T> Result<T> error(HttpStatus status, String message) {
        return new Result<>(false, status, message, null);
    }
}
