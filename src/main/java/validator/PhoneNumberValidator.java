package validator;

import java.util.Arrays;

public class PhoneNumberValidator {
    public static final String ERROR_MESSAGE = "Invalid phone number";

    private PhoneNumberValidator() {
    }

    public static boolean isValid(String... phoneNumbers) {
        return Arrays.stream(phoneNumbers).allMatch(phoneNumber -> phoneNumber.matches("^01[0-25]\\d{8}$"));
    }
}
