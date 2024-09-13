package validator;

import service.CategoryService;

import java.util.Arrays;

public class CategoryValidator {
    public static final String ERROR_MESSAGE = "Invalid category";
    private static final CategoryService categoryService = new CategoryService();

    private CategoryValidator() {
    }

    public static boolean isValid(String... categories) {
        return Arrays.stream(categories).map(Long::parseLong).allMatch(id -> categoryService.findById(id) != null);
    }
}
