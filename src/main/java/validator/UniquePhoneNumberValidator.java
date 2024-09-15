package validator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BuyerService;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/check-phone-number")
public class UniquePhoneNumberValidator extends HttpServlet {
    public static final String ERROR_MESSAGE = "Phone number already exists";
    private static final BuyerService buyerService = new BuyerService();

    public UniquePhoneNumberValidator() {
    }

    public static boolean isValid(String... phoneNumbers) {
        return Arrays.stream(phoneNumbers).noneMatch(buyerService::existsByPhoneNumber);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        String responseText = UniquePhoneNumberValidator.isValid(phoneNumber) ? "true" : ERROR_MESSAGE;
        response.setContentType("text/plain");
        response.getWriter().write(responseText);
    }
}
