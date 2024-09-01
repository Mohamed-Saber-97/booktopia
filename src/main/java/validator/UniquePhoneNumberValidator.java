package validator;

import service.BuyerService;

public class UniquePhoneNumberValidator {
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
