package org.example.booktopia.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Result {
    private Boolean success;
    private HttpStatus status;
    private String message;
    private Object data;

}
