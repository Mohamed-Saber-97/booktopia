package validator;

public class MinFieldLengthValidator {
    public static boolean isValid(int length, String... fields) {
        for (String field : fields) {
            if (field.length() < length) {
                return false;
            }
        }
        return true;
    }
}
