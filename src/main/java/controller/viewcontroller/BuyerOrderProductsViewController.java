package controller.viewcontroller;

import controller.BuyerController;
import controller.OrderController;
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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/buyer-order-products")
public class BuyerOrderProductsViewController extends HttpServlet {
    private BuyerController buyerController;
    private OrderController orderController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = ValidatorUtil.validateEntityId(request, "p", "order");
        if (!errors.isEmpty()) {
            request.setAttribute(ERROR, errors.get(ERROR));
            response.sendRedirect("/buyer-orders");
        } else {
            Buyer buyer = buyerController.findById(Long.parseLong(request.getParameter("p")));
            Order order = orderController.findById(Long.parseLong(request.getParameter("order")));
            if (buyer == null || order == null) {
                request.setAttribute(ERROR, "Buyer not found");
                response.sendRedirect("/buyer-profile");
            } else {
                List<OrderProduct> products = orderController.findProductsByBuyerId(buyer.getId(), order.getId());
                RequestDispatcher dispatcher = request.getRequestDispatcher("buyer-order-products.jsp");
                request.getSession().setAttribute(PAGE_TITLE, "Order products");
                request.setAttribute(PRODUCTS, products);
                request.getSession().setAttribute("tempBuyer", buyer);
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    public void init() {
        buyerController = new BuyerController();
        orderController = new OrderController();
    }
}
