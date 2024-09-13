package utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import model.Buyer;
import model.Category;
import model.Product;
import service.BuyerService;
import validator.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static utils.RequestAttributeUtil.*;
import static utils.RequestParameterUtil.*;

public class ValidatorUtil {

    private ValidatorUtil() {
    }

    private static Map<String, String> commonLogInValidation(ServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        if (!NotEmptyValidator.isValid(email, password)) {
            errors.put(ERROR, NotEmptyValidator.ERROR_MESSAGE);
        } else if (!EmailValidator.isValid(email)) {
            errors.put(ERROR, EmailValidator.ERROR_MESSAGE);
        } else if (!MinFieldLengthValidator.isValid(6, password)) {
            errors.put(ERROR, MinFieldLengthValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    private static Map<String, String> commonSignUpValidation(ServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        String name = request.getParameter(NAME);
        String birthday = request.getParameter(BIRTHDAY);
        String job = request.getParameter(JOB);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String country = request.getParameter(COUNTRY);
        String city = request.getParameter(CITY);
        String street = request.getParameter(STREET);
        String zipcode = request.getParameter(ZIPCODE);
        if (!NotEmptyValidator.isValid(name, birthday, job, email, password, confirmPassword, phoneNumber, country, city, street, zipcode)) {
            errors.put(ERROR, NotEmptyValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, name)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!EmailValidator.isValid(email)) {
            errors.put(ERROR, EmailValidator.ERROR_MESSAGE);
        } else if (!MinFieldLengthValidator.isValid(6, password)) {
            errors.put(ERROR, MinFieldLengthValidator.ERROR_MESSAGE);
        } else if (!PasswordConfirmationValidator.isValid(password, confirmPassword)) {
            errors.put(ERROR, PasswordConfirmationValidator.ERROR_MESSAGE);
        } else if (!PhoneNumberValidator.isValid(phoneNumber)) {
            errors.put(ERROR, PhoneNumberValidator.ERROR_MESSAGE);
        } else if (!CountryValidator.isValid(country)) {
            errors.put(ERROR, CountryValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(255, street)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, city)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!BirthdayValidator.isValid(LocalDate.parse(birthday))) {
            errors.put(ERROR, BirthdayValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(15, zipcode)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    public static Map<String, String> validateSignup(ServletRequest request) {
        Map<String, String> errors = new HashMap<>(commonSignUpValidation(request));

        String email = request.getParameter(EMAIL);
        String creditLimit = request.getParameter(CREDIT_LIMIT);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String[] interests = request.getParameterValues(CATEGORIES);

        if (!UniqueEmailValidator.isValid(email)) {
            errors.put(ERROR, UniqueEmailValidator.ERROR_MESSAGE);
        } else if (!CreditLimitValidator.isValid(creditLimit)) {
            errors.put(ERROR, CreditLimitValidator.ERROR_MESSAGE);
        } else if (!UniquePhoneNumberValidator.isValid(phoneNumber)) {
            errors.put(ERROR, UniquePhoneNumberValidator.ERROR_MESSAGE);
        } else if (!CategoryValidator.isValid(interests)) {
            errors.put(ERROR, CategoryValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    public static Map<String, String> validateBuyerUpdateProfile(ServletRequest request) {
        Map<String, String> errors = new HashMap<>(commonSignUpValidation(request));
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Buyer requestedBuyer = (Buyer) httpRequest.getSession().getAttribute(USER);
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
            errors.put(ERROR, EmailValidator.ERROR_MESSAGE);
        } else if (!newEmail.equals(savedBuyer.getAccount().getEmail()) && new BuyerService().existsByEmail(newEmail)) {
            errors.put(ERROR, "Email is already taken by someone else.");
        }

        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            if (!PhoneNumberValidator.isValid(newPhoneNumber)) {
                errors.put(ERROR, PhoneNumberValidator.ERROR_MESSAGE);
            } else if (!newPhoneNumber.equals(savedBuyer.getAccount().getPhoneNumber()) && new BuyerService().existsByPhoneNumber(newPhoneNumber)) {
                errors.put(ERROR, "Phone number is already taken by someone else.");
            }
        }
        String creditLimit = request.getParameter("creditLimit");
        if (!CreditLimitValidator.isValid(creditLimit)) {
            errors.put(ERROR, CreditLimitValidator.ERROR_MESSAGE);
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

    public static Map<String, String> commonBookValidation(ServletRequest request) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        String price = request.getParameter(PRICE);
        String quantity = request.getParameter(QUANTITY);
        String releaseDate = request.getParameter(RELEASE_DATE);
        String categoryId = request.getParameter(CATEGORY_ID);
        String isbn = request.getParameter(ISBN);
        String author = request.getParameter(AUTHOR);
        String name = request.getParameter(NAME);
        String description = request.getParameter(DESCRIPTION);
        Part imagePath = ((HttpServletRequest) request).getPart("imagePath");
        if (!NotEmptyValidator.isValid(price, quantity, releaseDate, categoryId, isbn, author, name, description)) {
            errors.put(ERROR, NotEmptyValidator.ERROR_MESSAGE);
        } else if (!PriceValidator.isValid(price)) {
            errors.put(ERROR, PriceValidator.ERROR_MESSAGE);
        } else if (!NumberValidator.isValid(quantity)) {
            errors.put(ERROR, NumberValidator.ERROR_MESSAGE);
        } else if (!DateValidator.isValid(releaseDate)) {
            errors.put(ERROR, DateValidator.ERROR_MESSAGE);
        } else if (!CategoryValidator.isValid(categoryId)) {
            errors.put(ERROR, CategoryValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, name)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, author)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(13, isbn)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(500, description)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!ObjectNotNullValidator.isValid(imagePath)) {
            errors.put(ERROR, ObjectNotNullValidator.ERROR_MESSAGE);
        } else if (!(imagePath.getSize() > 0)) {
            errors.put(ERROR, "Image is required");
        }
        return errors;
    }

    public static Map<String, String> validateAddBook(ServletRequest request) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>(commonBookValidation(request));
        String isbn = request.getParameter(ISBN);

        if (!UniqueIsbnValidator.isValid(isbn)) {
            errors.put(ERROR, UniqueIsbnValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    public static Map<String, String> validateUpdateBook(ServletRequest request) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>(commonBookValidation(request));
        String oldIsbn = ((Product) request.getAttribute(PRODUCT)).getIsbn();
        String newIsbn = request.getParameter(ISBN);

        if (!oldIsbn.equals(newIsbn) && !UniqueIsbnValidator.isValid(newIsbn)) {
            errors.put(ERROR, UniqueIsbnValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    public static Map<String, String> commonCategoryValidation(ServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        String name = request.getParameter(NAME);
        if (!NotEmptyValidator.isValid(name)) {
            errors.put(ERROR, NotEmptyValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, name)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    public static Map<String, String> validateAddCategory(ServletRequest request) {
        Map<String, String> errors = new HashMap<>(commonCategoryValidation(request));
        String name = request.getParameter(NAME);
        if (!UniqueCategoryNameValidator.isValid(name)) {
            errors.put(ERROR, UniqueCategoryNameValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    public static Map<String, String> validateUpdateCategory(ServletRequest request) {
        Map<String, String> errors = new HashMap<>(commonCategoryValidation(request));
        String oldName = ((Category) request.getAttribute(CATEGORY)).getName();
        String newName = request.getParameter(NAME);
        if (!oldName.equals(newName) && !UniqueCategoryNameValidator.isValid(newName)) {
            errors.put(ERROR, UniqueCategoryNameValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    public static Map<String, String> validateEntityId(ServletRequest request, String... parameters) {
        Map<String, String> errors = new HashMap<>();
        for (String parameter : parameters) {
            String id = request.getParameter(parameter);
            if (!NotEmptyValidator.isValid(id)) {
                errors.put(ERROR, NotEmptyValidator.ERROR_MESSAGE);
            } else if (!NumberValidator.isValid(id)) {
                errors.put(ERROR, NumberValidator.ERROR_MESSAGE);
            }
        }
        return errors;
    }
}
