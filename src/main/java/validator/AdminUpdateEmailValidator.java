package validator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Admin;
import service.AdminService;

import java.io.IOException;
import java.util.Objects;

import static utils.RequestAttributeUtil.USER;
import static utils.RequestParameterUtil.EMAIL;

@WebServlet("/admin-update-email")
public class AdminUpdateEmailValidator extends HttpServlet {

    public static final String ERROR_MESSAGE = "Email is already taken by someone else.";

    public AdminUpdateEmailValidator() {
    }

    public static boolean isValid(HttpServletRequest request) {
        String newEmail = request.getParameter(EMAIL);
        Admin current = (Admin) request.getSession().getAttribute(USER);
        if (current == null) {
            return false;
        }
        Admin entity = new AdminService().findByEmail(newEmail);
        if (entity == null) return true;
        return Objects.equals(current.getId(), entity.getId());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String responseText = AdminUpdateEmailValidator.isValid(request) ? "true" : ERROR_MESSAGE;
        response.setContentType("text/plain");  // Set response content type as plain text
        response.getWriter().write(responseText);
    }
}
