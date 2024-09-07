package validator;

import java.util.Arrays;

public class CreditLimitValidator {
    public static final String ERROR_MESSAGE = "Invalid credit limit";

    private CreditLimitValidator() {
    }

    public static boolean isValid(String... creditLimits) {
        return Arrays.stream(creditLimits).allMatch(creditLimit -> creditLimit.matches("^\\d+(\\.\\d+)?$"));
    }
}