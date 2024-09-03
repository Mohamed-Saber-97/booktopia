package controller;

import java.io.IOException;
import java.util.List;

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

@WebServlet(value = "")
public class HomeController extends HttpServlet {
    BuyerService buyerService;
    ProductService productService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        request.getSession().setAttribute("pageTitle", "Home");
        List<Product> interests;
        if (request.getSession().getAttribute("buyer") != null) {
            interests = buyerService.findInterestsByBuyerId(((Buyer) request.getSession().getAttribute("user")).getId());
            request.setAttribute("interests", interests);
        } else {
            interests = productService.findFirstX(16);
            request.setAttribute("interests", interests);
        }
        dispatcher.forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        buyerService = new BuyerService();
        productService = new ProductService();
    }
}
