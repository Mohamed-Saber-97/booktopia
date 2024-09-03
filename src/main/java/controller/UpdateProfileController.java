package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Buyer;

import java.io.IOException;

@WebServlet(value = "/update-profile")
public class UpdateProfileController extends HttpServlet {
    private BuyerController buyerController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        buyerController = new BuyerController();
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getAttribute("buyer");
        Buyer savedBuyer = buyerController.update(buyer);
        HttpSession session = request.getSession(false);
        session.setAttribute("user", savedBuyer);
        session.setAttribute("pageTitle", "Home");
        System.out.println("Redirecting to home page");
        response.sendRedirect(request.getContextPath() + "/");
    }
}
