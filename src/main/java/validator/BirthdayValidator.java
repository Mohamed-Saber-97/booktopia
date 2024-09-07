package validator;

import java.time.LocalDate;

public class BirthdayValidator {
    public static final String ERROR_MESSAGE = "Age should be older than 18 years old";

    public static boolean isValid(LocalDate birthday) {
        LocalDate allowedAge = LocalDate.now().minusYears(18);
        return birthday.isBefore(allowedAge);
    }
}
