package controller.viewcontroller;

import controller.BuyerController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import model.Order;
import model.OrderProduct;
import utils.ValidatorUtil;
import validator.NotEmptyValidator;
import validator.NumberValidator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/buyer-orders")
public class BuyerOrdersViewController extends HttpServlet {
    private BuyerController buyerController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = ValidatorUtil.validateEntityIdGET(request);
        if (!errors.isEmpty()) {
            request.setAttribute(ERROR, errors.get(ERROR));
            response.sendRedirect("/buyer-profile");
        } else {
            Buyer buyer = new BuyerController().findById(Long.parseLong(request.getParameter("p")));
            if (buyer == null) {
                request.setAttribute(ERROR, "Buyer not found");
                response.sendRedirect("/buyer-profile");
            } else {
                String pageNumberString = request.getParameter("page");
                int pageNumber = NotEmptyValidator.isValid(pageNumberString) && NumberValidator.isValid(pageNumberString) ? Integer.parseInt(pageNumberString) : 0;
                List<Order> orders = buyerController.searchOrders(buyer.getId(), pageNumber, 16);
                orders.forEach(order -> order.getOrderProducts().removeIf(OrderProduct::getIsDeleted));
                RequestDispatcher dispatcher = request.getRequestDispatcher("buyer-orders.jsp");
                request.getSession().setAttribute(PAGE_TITLE, "Buyer's Orders");
                request.setAttribute(ORDERS, orders);
                request.getSession().setAttribute("tempBuyer", buyer);
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    public void init() {
        buyerController = new BuyerController();
    }
}
