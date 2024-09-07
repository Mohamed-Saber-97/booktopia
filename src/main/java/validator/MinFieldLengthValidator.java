package validator;

public class MinFieldLengthValidator {
    public static final String ERROR_MESSAGE = "Password must be at least 6 characters long";
    public static boolean isValid(int length, String... fields) {
        for (String field : fields) {
            if (field.length() < length) {
                return false;
            }
        }
        return true;
    }
}
