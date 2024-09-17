package validator;

import java.util.Arrays;

public class PriceValidator {
    public static final String ERROR_MESSAGE = "Invalid price";

    private PriceValidator() {
    }

    public static boolean isValid(String... prices) {
        return Arrays.stream(prices).allMatch(price -> price.matches("^(?!0$)\\d+(\\.\\d{1,2})?$"));
    }
}
