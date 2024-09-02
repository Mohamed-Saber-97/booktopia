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

@WebServlet(value = "")
public class HomeController extends HttpServlet {
    BuyerService buyerService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        request.getSession().setAttribute("pageTitle", "Home");
        if (request.getSession().getAttribute("user") != null) {
            List<Product> interests = buyerService.findInterestsByBuyerId(((Buyer) request.getSession().getAttribute("user")).getId());
            request.setAttribute("interests", interests);
        } else {
            // TODO: get 16 products
        }
        dispatcher.forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        buyerService = new BuyerService();
    }
}
