package validator;

import java.time.LocalDate;

public class BirthdayValidator {
    public static final String ERROR_MESSAGE = "Age should be older than 18 years old";

    private BirthdayValidator() {
    }

    public static boolean isValid(LocalDate birthday) {
        return birthday.isBefore(LocalDate.now().minusYears(18));
    }
}