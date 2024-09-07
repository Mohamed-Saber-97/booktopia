package validator;

public class PhoneNumberValidator {
    public static final String ERROR_MESSAGE = "Invalid phone number";

    public static boolean isValid(String... phoneNumbers) {
        for (String phoneNumber : phoneNumbers) {
            if (!phoneNumber.matches("^01[0-25]\\d{8}$")) {
                return false;
            }
        }
        return true;
    }
}
