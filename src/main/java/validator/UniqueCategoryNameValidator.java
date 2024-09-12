package validator;

import controller.CategoryController;

import java.util.Arrays;

public class UniqueCategoryNameValidator {
    public static final String ERROR_MESSAGE = "Name already exists";
    private static final CategoryController categoryController = new CategoryController();

    public static boolean isValid(String... names) {
        return Arrays.stream(names).noneMatch(categoryController::existsByName);
    }
}
