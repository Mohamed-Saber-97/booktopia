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

import java.io.IOException;
import java.util.List;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/orders")
public class OrdersViewController extends HttpServlet {
    private BuyerController buyerController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
        if (buyer == null) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            List<Order> orders = buyerController.searchOrders(buyer.getId(), 0, 16);
            orders.forEach(order -> order.getOrderProducts().removeIf(OrderProduct::getIsDeleted));
            RequestDispatcher dispatcher = request.getRequestDispatcher("orders.jsp");
            request.getSession().setAttribute(PAGE_TITLE, "Orders");
            request.setAttribute(ORDERS, orders);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void init() {
        buyerController = new BuyerController();
    }
}
