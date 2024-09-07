package validator;

public class PasswordConfirmationValidator {
    public static final String ERROR_MESSAGE = "Password and confirm password do not match";

    public static boolean isValid(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }
}
