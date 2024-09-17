package validator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import service.BuyerService;

import java.io.IOException;
import java.util.Objects;

import static utils.RequestAttributeUtil.USER;
import static utils.RequestParameterUtil.PHONE_NUMBER;

@WebServlet("/buyer-update-phone-number")
public class BuyerUpdatePhoneNumberValidator extends HttpServlet {

    public static final String ERROR_MESSAGE = "Phone number is already taken by someone else.";

    public BuyerUpdatePhoneNumberValidator() {
    }

    public static boolean isValid(HttpServletRequest request) {
        String newPhoneNumber = request.getParameter(PHONE_NUMBER);
        Buyer current = (Buyer) request.getSession().getAttribute(USER);
        if (current == null) {
            return false;
        }
        Buyer entity = new BuyerService().findByPhoneNumber(newPhoneNumber);
        if (entity == null) return true;
        return Objects.equals(current.getId(), entity.getId());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String responseText = BuyerUpdatePhoneNumberValidator.isValid(request) ? "true" : ERROR_MESSAGE;
        response.setContentType("text/plain");  // Set response content type as plain text
        response.getWriter().write(responseText);
    }
}
