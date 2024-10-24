package org.example.booktopia.error;

import org.example.booktopia.utils.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public Result<?> handleRecordNotFound(RecordNotFoundException ex) {

        return Result.error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public Result<?> handleDuplicateRecordFound(DuplicateRecordException ex) {

        return Result.error(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(IllegalValueException.class)
    public Result<?> handleIllegalValue(IllegalValueException ex) {

        return Result.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public Result<?> handleInvalidLogin(InvalidLoginCredentialsException ex) {

        return Result.error(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<String>();
        ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .forEach(errors::add);
        ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .forEach(errors::add);
        ErrorResponse error = new ErrorResponse(ex.toString(), errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
