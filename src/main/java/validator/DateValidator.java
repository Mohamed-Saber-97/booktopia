package validator;

import java.util.Arrays;

public class DateValidator {
    public static final String ERROR_MESSAGE = "Invalid date";

    private DateValidator() {
    }

    public static boolean isValid(String... dates) {
        return Arrays.stream(dates).allMatch(date -> date.matches("^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$"));
    }
}
