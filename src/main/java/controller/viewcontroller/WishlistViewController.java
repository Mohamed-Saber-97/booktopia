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

@WebServlet(value = "/wishlist")
public class WishlistViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
        if (!CartValidator.isValid(buyer.getCart(), buyer)) {
            request.setAttribute(ERROR, CartValidator.ERROR_MESSAGE);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("wishlist.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Wishlist");
        dispatcher.forward(request, response);
    }
}