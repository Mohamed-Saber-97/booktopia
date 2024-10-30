package org.example.booktopia.error;

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

//@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> handleRecordNotFound(RecordNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), Collections.singletonList(ex.getMessage()));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<?> handleDuplicateRecordFound(DuplicateRecordException ex) {

        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), Collections.singletonList(ex.getMessage()));

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(IllegalValueException.class)
    public ResponseEntity<?> handleIllegalValue(IllegalValueException ex) {
        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(error);
    }

    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public ResponseEntity<?> handleInvalidLogin(InvalidLoginCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(error);
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
