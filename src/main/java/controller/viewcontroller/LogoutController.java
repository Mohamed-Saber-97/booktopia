package controller.viewcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Admin;
import model.Buyer;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var buyer = request.getSession().getAttribute(BUYER);
        var admin = request.getSession().getAttribute(ADMIN);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        if (buyer != null) {
            Buyer buyerObject = (Buyer) request.getSession().getAttribute(USER);
            request.setAttribute(SUCCESS, "Thanks for stopping by, %s".formatted(buyerObject.getAccount().getName()));
        } else if (admin != null) {
            Admin adminObject = (Admin) request.getSession().getAttribute(USER);
            request.setAttribute(SUCCESS, "Thank you for managing the site, %s".formatted(adminObject.getAccount().getName()));
        }
        request.getSession().invalidate();
        dispatcher.forward(request, response);

    }
}
