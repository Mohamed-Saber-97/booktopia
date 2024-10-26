package org.example.booktopia.validator;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.booktopia.controller.ProductController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

import static org.example.booktopia.utils.RequestParameterUtil.ISBN;

@Controller
@AllArgsConstructor
public class UniqueIsbnValidator extends HttpServlet {
    public static final String ERROR_MESSAGE = "ISBN already exists";
    public static final String ERROR_MESSAGE_LENGTH = "Invalid ISBN length";
    public static final String VALID_MESSAGE = "true";
    public final ProductController productController;

    public static boolean isValidLength(String isbn) {
        return isbn.length() == 10 || isbn.length() == 13;
    }

    public boolean isValid(String... isbns) {
        return Arrays.stream(isbns).noneMatch(productController::existsByIsbn);
    }

    @PostMapping("/unique-isbn")
    public ResponseEntity<String> validate(HttpServletRequest request) {
        String isbn = request.getParameter(ISBN);
        String responseText;
        if (!isValidLength(isbn)) {
            responseText = ERROR_MESSAGE_LENGTH;
        } else if (!isValid(isbn)) {
            responseText = ERROR_MESSAGE;
        } else {
            responseText = VALID_MESSAGE;

        }
        return ResponseEntity.ok(responseText);
    }
}
