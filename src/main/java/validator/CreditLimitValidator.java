package validator;

public class CreditLimitValidator {
    public static boolean isValid(String... creditLimits) {
        for (String creditLimit : creditLimits) {
            if (!creditLimit.matches("^\\d+(\\.\\d+)?$")) {
                return false;
            }
        }
        return true;
    }
}
