package controller.viewcontroller;

import com.google.gson.Gson;
import controller.BuyerController;
import dto.OrderDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.OrderToOrderDto;
import model.Buyer;
import model.Order;
import model.OrderProduct;
import validator.NotEmptyValidator;
import validator.NumberValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/next-orders")
public class NextOrdersController extends HttpServlet {
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
            List<OrderDto> orderDtos = new ArrayList<>();
            OrderToOrderDto orderToOrderDto = new OrderToOrderDto();
            for (int i = 0; i < orders.size(); i++) {
                orderDtos.add(orderToOrderDto.convert(orders.get(i)));
            }
            Gson gson = new Gson();
            String jsonOrders = gson.toJson(orderDtos);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"orders\":" + jsonOrders + "}");
        }
    }

    @Override
    public void init() {
        buyerController = new BuyerController();
    }
}
