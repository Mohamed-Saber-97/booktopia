package validator;

public class EmailValidator {
    public static final String ERROR_MESSAGE = "Invalid email";
    public static boolean isValid(String... emails) {
        for (String email : emails) {
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                return false;
            }
        }
        return true;
    }
}
