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

import static utils.RequestAttributeUtil.USER;

@WebServlet(value = "/next-orders")
public class NextOrdersController extends HttpServlet {
    private BuyerController buyerController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumberString = request.getParameter("page");
        int pageNumber = NotEmptyValidator.isValid(pageNumberString) && NumberValidator.isValid(pageNumberString) ? Integer.parseInt(pageNumberString) : 0;
        Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
        if (buyer == null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            List<Order> orders = buyerController.searchOrders(buyer.getId(), pageNumber, 16);
            orders.forEach(order -> order.getOrderProducts().removeIf(OrderProduct::getIsDeleted));
            String json = Jsonify.jsonifyOrders(orders);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"orders\":" + json + "}");
        }
    }

    @Override
    public void init() {
        buyerController = new BuyerController();
    }
}
