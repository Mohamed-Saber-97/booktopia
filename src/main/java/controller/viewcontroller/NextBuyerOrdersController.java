package controller.viewcontroller;

import controller.BuyerController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import model.Order;
import model.OrderProduct;
import utils.Jsonify;
import validator.NotEmptyValidator;
import validator.NumberValidator;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/next-buyer-orders")
public class NextBuyerOrdersController extends HttpServlet {
    private BuyerController buyerController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumberString = request.getParameter("page");
        int pageNumber = NotEmptyValidator.isValid(pageNumberString) && NumberValidator.isValid(pageNumberString) ? Integer.parseInt(pageNumberString) : 0;
        Buyer buyer = (Buyer) request.getSession().getAttribute("tempBuyer");
        if (buyer == null) {
            request.setAttribute("error", "Buyer not found");
            response.sendRedirect("/buyers");
        } else {
            List<Order> orders = buyerController.searchOrders(buyer.getId(), pageNumber, 16);
            orders.forEach(order -> order.getOrderProducts().removeIf(OrderProduct::getIsDeleted));
            String jsonOrders = Jsonify.jsonifyOrders(orders);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write("{\"orders\":" + jsonOrders + "}");
        }
    }

    @Override
    public void init() {
        buyerController = new BuyerController();
    }
}
