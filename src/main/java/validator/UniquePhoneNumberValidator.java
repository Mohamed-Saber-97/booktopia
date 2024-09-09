package validator;

import service.BuyerService;

import java.util.Arrays;

public class UniquePhoneNumberValidator {
    public static final String ERROR_MESSAGE = "Phone number already exists";
    private static final BuyerService buyerService = new BuyerService();

    private UniquePhoneNumberValidator() {
    }

    public static boolean isValid(String... phoneNumbers) {
        return Arrays.stream(phoneNumbers).noneMatch(buyerService::existsByPhoneNumber);
    }
}
