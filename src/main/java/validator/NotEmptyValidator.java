package validator;

public class NotEmptyValidator {
    public static boolean isValid(String... values) {
        for (String value : values) {
            if (value == null || value.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
