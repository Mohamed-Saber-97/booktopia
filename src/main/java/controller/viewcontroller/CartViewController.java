package controller.viewcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import validator.CartValidator;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/cart")
public class CartViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
        if (!CartValidator.isValid(buyer)) {
            request.setAttribute(ERROR, CartValidator.ERROR_MESSAGE);
            request.getSession().setAttribute(USER, buyer);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Cart");
        dispatcher.forward(request, response);
    }
}
