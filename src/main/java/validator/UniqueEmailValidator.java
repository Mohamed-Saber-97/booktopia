package validator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BuyerService;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/check-unique-email")
public class UniqueEmailValidator extends HttpServlet {

    public static final String ERROR_MESSAGE = "Email already exists";
    private static final BuyerService buyerService = new BuyerService();

    public UniqueEmailValidator() {
    }

    public static boolean isValid(String... emails) {
        return Arrays.stream(emails).noneMatch(buyerService::existsByEmail);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String responseText = UniqueEmailValidator.isValid(email) ? "true" : ERROR_MESSAGE;
        response.setContentType("text/plain");  // Set response content type as plain text
        response.getWriter().write(responseText);
    }
}
