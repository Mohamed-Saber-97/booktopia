package validator;

public class MaxFieldLengthValidator {
    private static int length = 100;
    public static final String ERROR_MESSAGE = "Name must be up to %d characters".formatted(length);

    public static boolean isValid(int length, String... fields) {
        MaxFieldLengthValidator.length = length;
        for (String field : fields) {
            if (field.length() > length) {
                return false;
            }
        }
        return true;
    }
}
