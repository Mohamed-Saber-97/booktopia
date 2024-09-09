package controller.viewcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Buyer;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/login")
public class BuyerLoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("buyer-login.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Buyer login");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getAttribute(USER);
        HttpSession session = request.getSession(true);
        session.setAttribute(USER, buyer);
        session.setAttribute(BUYER, YES);
        session.setAttribute(PAGE_TITLE, "Home");
        response.sendRedirect(request.getContextPath() + "/");
    }
}
