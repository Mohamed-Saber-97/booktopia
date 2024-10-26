package org.example.booktopia.validator;

import lombok.AllArgsConstructor;
import org.example.booktopia.service.CategoryService;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

@Controller
@AllArgsConstructor
public class CategoryValidator {
    public static final String ERROR_MESSAGE = "Invalid category";
    private final CategoryService categoryService;

    public boolean isValid(Long... categories) {
        return Arrays.stream(categories).allMatch(id -> categoryService.findById(id) != null);
    }
}
