package controller.viewcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import validator.WishlistValidator;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/wishlist")
public class WishlistViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
        if (!WishlistValidator.isValid(buyer)) {
            request.setAttribute(ERROR, WishlistValidator.ERROR_MESSAGE);
            request.getSession().setAttribute(USER, buyer);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("wishlist.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Wishlist");
        dispatcher.forward(request, response);
    }
}
