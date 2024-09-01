package validator;

public class PasswordConfirmationValidator {
    public static boolean isValid(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }
}
