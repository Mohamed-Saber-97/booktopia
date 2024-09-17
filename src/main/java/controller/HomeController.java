package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import model.Product;
import service.BuyerService;
import service.ProductService;

import java.io.IOException;
import java.util.List;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "")
public class HomeController extends HttpServlet {
    private transient BuyerService buyerService;
    private transient ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Home");
        request.setAttribute(SUCCESS, request.getSession().getAttribute(SUCCESS));
        request.getSession().removeAttribute(SUCCESS);
        List<Product> interests;
        if (request.getSession().getAttribute(BUYER) != null) {
            interests = buyerService.findInterestsByBuyerId(((Buyer) request.getSession().getAttribute(USER)).getId());
            request.setAttribute(INTERESTS, interests);
        } else {
            interests = productService.findFirstX(16);
            request.setAttribute(INTERESTS, interests);
        }
        dispatcher.forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        buyerService = new BuyerService();
        productService = new ProductService();
    }
}
