package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Account;
import model.Address;
import model.Admin;
import model.Buyer;
import validator.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebFilter(urlPatterns = "/update-profile")
public class UpdateProfileFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        if (method.equals("POST")) {
            String name = request.getParameter("name");
            String birthday = request.getParameter("birthday");
            String job = request.getParameter("job");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String creditLimit = null;
            if (((HttpServletRequest) request).getSession().getAttribute("buyer") != null) {
                creditLimit = request.getParameter("creditLimit");
            } else {
                creditLimit = "1";
            }
            String phoneNumber = request.getParameter("phoneNumber");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            String street = request.getParameter("street");
            String zipcode = request.getParameter("zipcode");
            boolean isValid = true;
            if (!NotEmptyValidator.isValid(name, birthday, job, email, password, confirmPassword, creditLimit,
                    phoneNumber, country, city, street, zipcode)) {
                request.setAttribute("error", "All fields are required");
                isValid = false;

            } else if (!MaxFieldLengthValidator.isValid(100, name)) {
                request.setAttribute("error", "Name must be up to 100 characters");
                isValid = false;
            } else if (!EmailValidator.isValid(email)) {
                request.setAttribute("error", "Invalid email");
                isValid = false;
            } else if (!MinFieldLengthValidator.isValid(6, password)) {
                request.setAttribute("error", "Password must be at least 6 characters long");
                isValid = false;
            } else if (!MaxFieldLengthValidator.isValid(100, job)) {
                request.setAttribute("error", "Job title must be up to 100 characters");
                isValid = false;
            } else if (!PasswordConfirmationValidator.isValid(password, confirmPassword)) {
                request.setAttribute("error", "Password and confirm password do not match");
                isValid = false;
            } else if (!CreditLimitValidator.isValid(creditLimit)) {
                request.setAttribute("error", "Invalid credit limit");
                isValid = false;
            } else if (!PhoneNumberValidator.isValid(phoneNumber)) {
                request.setAttribute("error", "Invalid phone number");
                isValid = false;
            } else if (!CountryValidator.isValid(country)) {
                request.setAttribute("error", "Invalid country");
                isValid = false;
            } else if (!MaxFieldLengthValidator.isValid(255, street)) {
                request.setAttribute("error", "Street name must be up to 255 characters");
                isValid = false;
            } else if (!MaxFieldLengthValidator.isValid(100, city)) {
                request.setAttribute("error", "City name must be up to 100 characters");
                isValid = false;
            } else if (!BirthdayValidator.isValid(LocalDate.parse(birthday))) {
                request.setAttribute("error", "Age should be older than 18 years old");
                isValid = false;
            } else if (!MaxFieldLengthValidator.isValid(15, zipcode)) {
                request.setAttribute("error", "Zipcode must be up to 15 characters");
                isValid = false;
            }
            if (!isValid) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
                ((HttpServletRequest) request).getSession().setAttribute("pageTitle", "Update Profile");
                dispatcher.forward(request, response);
            } else if (((HttpServletRequest) request).getSession().getAttribute("buyer") != null) {
                Buyer buyer = new Buyer();
                buyer.setAccount(new Account());
                buyer.getAccount().setAddress(new Address());
                buyer.getAccount().setName(name);
                buyer.getAccount().setBirthday(LocalDate.parse(birthday));
                buyer.getAccount().setJob(job);
                buyer.getAccount().setEmail(email);
                buyer.getAccount().setPassword(password);
                buyer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(creditLimit)));
                buyer.getAccount().setPhoneNumber(phoneNumber);
                buyer.getAccount().getAddress().setCountry(country);
                buyer.getAccount().getAddress().setCity(city);
                buyer.getAccount().getAddress().setStreet(street);
                buyer.getAccount().getAddress().setZipcode(zipcode);
                request.setAttribute("buyer", buyer);
                chain.doFilter(request, response);
            } else if (((HttpServletRequest) request).getSession().getAttribute("admin") != null) {
                Admin admin = new Admin();
                admin.setAccount(new Account());
                admin.getAccount().setAddress(new Address());
                admin.getAccount().setName(name);
                admin.getAccount().setBirthday(LocalDate.parse(birthday));
                admin.getAccount().setJob(job);
                admin.getAccount().setEmail(email);
                admin.getAccount().setPassword(password);
                admin.getAccount().setPhoneNumber(phoneNumber);
                admin.getAccount().getAddress().setCountry(country);
                admin.getAccount().getAddress().setCity(city);
                admin.getAccount().getAddress().setStreet(street);
                admin.getAccount().getAddress().setZipcode(zipcode);
                request.setAttribute("admin", admin);
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
