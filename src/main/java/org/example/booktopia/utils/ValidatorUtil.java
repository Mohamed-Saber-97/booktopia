package org.example.booktopia.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.NewProductDto;
import org.example.booktopia.validator.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.example.booktopia.utils.RequestAttributeUtil.ERROR;

@Controller
@AllArgsConstructor
public class ValidatorUtil {
    private final CategoryValidator categoryValidator;
    private final UniqueIsbnValidator uniqueIsbnValidator;

    public Map<String, String> commonBookValidation(HttpServletRequest request, NewProductDto productDto) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        BigDecimal price = productDto.price();
        Integer quantity = productDto.quantity();
        LocalDate releaseDate = productDto.releaseDate();
        Long categoryId = productDto.categoryId();
        String isbn = productDto.isbn();
        String author = productDto.author();
        String name = productDto.name();
        String description = productDto.description();
        Part imagePath = request.getPart("imagePath");
        if (!NotEmptyValidator.isValid(isbn, author, name, description)) {
            errors.put(ERROR, NotEmptyValidator.ERROR_MESSAGE);
        } else if (!categoryValidator.isValid(categoryId)) {
            errors.put(ERROR, CategoryValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, name)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, author)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(13, isbn)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(500, description)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!ObjectNotNullValidator.isValid(imagePath, price, quantity, releaseDate, categoryId)) {
            errors.put(ERROR, ObjectNotNullValidator.ERROR_MESSAGE);
        } else if (!(imagePath.getSize() > 0)) {
            errors.put(ERROR, "Image is required");
        }
        return errors;
    }

    public Map<String, String> validateAddBook(HttpServletRequest request, NewProductDto productDto) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>(commonBookValidation(request, productDto));
        String isbn = productDto.isbn();

        if (!uniqueIsbnValidator.isValid(isbn)) {
            errors.put(ERROR, UniqueIsbnValidator.ERROR_MESSAGE);
        }
        return errors;
    }
}
