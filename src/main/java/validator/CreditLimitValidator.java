package validator;

public class CreditLimitValidator {
    public static final String ERROR_MESSAGE =  "Invalid credit limit";

    public static boolean isValid(String... creditLimits) {
        for (String creditLimit : creditLimits) {
            if (!creditLimit.matches("^\\d+(\\.\\d+)?$")) {
                return false;
            }
        }
        return true;
    }
}
