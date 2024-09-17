package controller.viewcontroller;

import controller.BuyerController;
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
    private transient BuyerController buyerController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
        request.getSession().setAttribute(USER, buyerController.findById(buyer.getId()));
//        request.getSession().setAttribute(USER, buyer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Cart");
        if (!CartValidator.isValid(buyer)) {
            request.setAttribute(ERROR, CartValidator.ERROR_MESSAGE);
            dispatcher.forward(request, response);
        } else if (request.getSession().getAttribute(ERROR) != null) {
            request.setAttribute(ERROR, request.getSession().getAttribute(ERROR));
            request.getSession().removeAttribute(ERROR);
            dispatcher.forward(request, response);
        } else {
            request.setAttribute(SUCCESS, "Your Cart is Up to Date!");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        buyerController = new BuyerController();
    }
}
