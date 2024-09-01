package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Account;
import model.Address;
import model.Buyer;
import validator.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebFilter(urlPatterns = "/signup")
public class SignupFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        if (method.equals("POST")) {
            String name = request.getParameter("name");
            String birthday = request.getParameter("birthday");
            String job = request.getParameter("job");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String creditLimit = request.getParameter("creditLimit");
            String phoneNumber = request.getParameter("phoneNumber");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            String street = request.getParameter("street");
            String zipcode = request.getParameter("zipcode");
            boolean isValid = true;
            if (!NotEmptyValidator.isValid(name, birthday, job, email, password, confirmPassword, creditLimit, phoneNumber, country, city, street, zipcode)) {
                request.setAttribute("error", "All fields are required");
                isValid = false;

            } else if (!EmailValidator.isValid(email)) {
                request.setAttribute("error", "Invalid email");
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
            }
            if (!isValid) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
                request.setAttribute("pageTitle", "Sign up");
                dispatcher.forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
