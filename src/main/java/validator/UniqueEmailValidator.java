package validator;

import service.BuyerService;

import java.util.Arrays;

public class UniqueEmailValidator {
    public static final String ERROR_MESSAGE = "Email already exists";
    private static final BuyerService buyerService = new BuyerService();

    private UniqueEmailValidator() {
    }

    public static boolean isValid(String... emails) {
        return Arrays.stream(emails).noneMatch(buyerService::existsByEmail);
    }
}
