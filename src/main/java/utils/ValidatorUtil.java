package utils;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import model.Buyer;
import service.BuyerService;
import validator.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ValidatorUtil {
    private static final String ERROR = "error";

    private ValidatorUtil() {
    }

    private static Map<String, String> commonValidation(ServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phoneNumber = request.getParameter("phoneNumber");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String zipcode = request.getParameter("zipcode");
        if (!NotEmptyValidator.isValid(name, birthday, job, email, password, confirmPassword, phoneNumber, country, city, street, zipcode)) {
            errors.put(ERROR, "All fields are required");
        } else if (!MaxFieldLengthValidator.isValid(100, name)) {
            errors.put(ERROR, "Name must be up to 100 characters");
        } else if (!EmailValidator.isValid(email)) {
            errors.put(ERROR, "Invalid email");
        } else if (!MinFieldLengthValidator.isValid(6, password)) {
            errors.put(ERROR, "Password must be at least 6 characters long");
        } else if (!PasswordConfirmationValidator.isValid(password, confirmPassword)) {
            errors.put(ERROR, "Password and confirm password do not match");
        } else if (!PhoneNumberValidator.isValid(phoneNumber)) {
            errors.put(ERROR, "Invalid phone number");
        } else if (!CountryValidator.isValid(country)) {
            errors.put(ERROR, "Invalid country");
        } else if (!MaxFieldLengthValidator.isValid(255, street)) {
            errors.put(ERROR, "Street name must be up to 255 characters");
        } else if (!MaxFieldLengthValidator.isValid(100, city)) {
            errors.put(ERROR, "City name must be up to 100 characters");
        } else if (!BirthdayValidator.isValid(LocalDate.parse(birthday))) {
            errors.put(ERROR, "Age should be older than 18 years old");
        } else if (!MaxFieldLengthValidator.isValid(15, zipcode)) {
            errors.put(ERROR, "Zipcode must be up to 15 characters");
        }
        return errors;
    }

    public static Map<String, String> validateSignup(ServletRequest request) {
        Map<String, String> errors = new HashMap<>(commonValidation(request));

        String email = request.getParameter("email");
        String creditLimit = request.getParameter("creditLimit");
        String phoneNumber = request.getParameter("phoneNumber");

        if (!UniqueEmailValidator.isValid(email)) {
            errors.put(ERROR, "Email already exists");
        } else if (!CreditLimitValidator.isValid(creditLimit)) {
            errors.put(ERROR, "Invalid credit limit");
        } else if (!UniquePhoneNumberValidator.isValid(phoneNumber)) {
            errors.put(ERROR, "Phone number already exists");
        }
        return errors;
    }

    public static Map<String, String> validateBuyerUpdateProfile(ServletRequest request) {
        Map<String, String> errors = new HashMap<>(commonValidation(request));
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Buyer requestedBuyer = (Buyer) httpRequest.getSession().getAttribute("user");
        if (requestedBuyer == null) {
            errors.put(ERROR, "User not found in session.");
            return errors;
        }
        Buyer savedBuyer = new BuyerService().findById(requestedBuyer.getId());
        if (savedBuyer == null) {
            errors.put(ERROR, "Buyer not found in the database.");
            return errors;
        }
        String newEmail = requestedBuyer.getAccount().getEmail();
        String newPhoneNumber = requestedBuyer.getAccount().getPhoneNumber();

        if (!EmailValidator.isValid(newEmail)) {
            errors.put(ERROR, "Invalid email");
        } else if (!newEmail.equals(savedBuyer.getAccount().getEmail()) && new BuyerService().existsByEmail(newEmail)) {
            errors.put(ERROR, "Email is already taken by someone else.");
        }

        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            if (!PhoneNumberValidator.isValid(newPhoneNumber)) {
                errors.put(ERROR, "Invalid phone number.");
            } else if (!newPhoneNumber.equals(savedBuyer.getAccount().getPhoneNumber()) && new BuyerService().existsByPhoneNumber(newPhoneNumber)) {
                errors.put(ERROR, "Phone number is already taken by someone else.");
            }
        }
        String creditLimit = request.getParameter("creditLimit");
        if (!CreditLimitValidator.isValid(creditLimit)) {
            errors.put(ERROR, "Invalid credit limit");
        }
        return errors;
    }

    public static Map<String, String> validateAdminUpdateProfile(ServletRequest request) {
        return commonSignUpValidation(request);
    }
    public static Map<String, String> validateAdminLogin(ServletRequest request) {
        return commonLogInValidation(request);
    }
    public static Map<String, String> validateBuyerLogin(ServletRequest request) {
        return commonLogInValidation(request);
    }
}
