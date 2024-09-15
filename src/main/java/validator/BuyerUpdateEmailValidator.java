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
import static utils.RequestParameterUtil.EMAIL;

@WebServlet("/buyer-update-email")
public class BuyerUpdateEmailValidator extends HttpServlet {

    public static final String ERROR_MESSAGE = "Email is already taken by someone else.";

    public BuyerUpdateEmailValidator() {
    }

    public static boolean isValid(HttpServletRequest request) {
        String newEmail = request.getParameter(EMAIL);
        Buyer current = (Buyer) request.getSession().getAttribute(USER);
        if (current == null) {
            return false;
        }
        Buyer entity = new BuyerService().findByEmail(newEmail);
        if(entity == null)
            return true;
//        String newEmail = requestedBuyer.getAccount().getEmail();
        return Objects.equals(current.getId(), entity.getId());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String responseText = BuyerUpdateEmailValidator.isValid(request) ? "true" : ERROR_MESSAGE;
        response.setContentType("text/plain");  // Set response content type as plain text
        response.getWriter().write(responseText);
    }
}
