package validator;

import controller.ProductController;

import java.util.Arrays;

public class UniqueIsbnValidator {
    public static final String ERROR_MESSAGE = "ISBN already exists";
    public static final ProductController productController = new ProductController();

    private UniqueIsbnValidator() {
    }

    public static boolean isValid(String... isbns) {
        return Arrays.stream(isbns).noneMatch(productController::existsByIsbn);
    }
}
