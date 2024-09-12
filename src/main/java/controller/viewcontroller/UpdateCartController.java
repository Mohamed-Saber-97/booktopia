package controller.viewcontroller;

import error.InsufficientFunds;
import error.InsufficientStock;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import service.BuyerService;
import validator.CartValidator;
import validator.CheckoutValidator;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/update-cart")
public class UpdateCartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
        if (!CheckoutValidator.isValid(buyer)) {
            request.setAttribute(ERROR, CartValidator.ERROR_MESSAGE);
        } else {
            try {
                new BuyerService().checkout(buyer);
                request.getSession().setAttribute(USER, buyer);
            } catch (InsufficientStock | InsufficientFunds e) {
                request.setAttribute(ERROR, e.getMessage());
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Cart");
        dispatcher.forward(request, response);
    }
}
