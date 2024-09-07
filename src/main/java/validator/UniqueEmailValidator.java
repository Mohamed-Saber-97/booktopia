package validator;

import service.BuyerService;

public class UniqueEmailValidator {
    public static final String ERROR_MESSAGE = "Email already exists";

    public static boolean isValid(String... emails) {
        BuyerService buyerService = new BuyerService();
        for (String email : emails) {
            if (buyerService.existsByEmail(email)) {
                return false;
            }
        }
        return true;
    }
}
