package validator;

import java.time.LocalDate;

public class BirthdayValidator {
    public static boolean isValid(LocalDate birthday) {
        LocalDate allowedAge = LocalDate.now().minusYears(18);
        return birthday.isBefore(allowedAge);
    }
}
