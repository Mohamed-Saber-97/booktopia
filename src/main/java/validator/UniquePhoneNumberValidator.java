package validator;

import service.BuyerService;

public class UniquePhoneNumberValidator {
    public static final String ERROR_MESSAGE =  "Phone number already exists";

    public static boolean isValid(String... phoneNumbers) {
        BuyerService buyerService = new BuyerService();
        for (String phoneNumber : phoneNumbers) {
            if (buyerService.existsByPhoneNumber(phoneNumber)) {
                return false;
            }
        }
        return true;
    }
}
