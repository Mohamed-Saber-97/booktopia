package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;
import model.Buyer;

import java.io.IOException;

@WebServlet(value = "/update-profile")
public class UpdateProfileController extends HttpServlet {
    private static final String ADMIN = "admin";
    private static final String USER = "user";
    private static final String BUYER = "buyer";

    private BuyerController buyerController;
private AdminController adminController;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        buyerController = new BuyerController();
        adminController = new AdminController();
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(BUYER) != null) {
            Buyer buyer = (Buyer) request.getSession().getAttribute("user");
            Buyer savedBuyer = buyerController.update(buyer);
            HttpSession session = request.getSession();
            session.setAttribute(USER, savedBuyer);
            session.setAttribute(BUYER, "Y");
            session.setAttribute("pageTitle", "Home");
            response.sendRedirect(request.getContextPath() + "/");
        } else if (request.getSession().getAttribute(ADMIN) != null) {
            Admin admin = (Admin) request.getAttribute(ADMIN);
            Admin savedAdmin = adminController.update(admin);
            HttpSession session = request.getSession();
            session.setAttribute(USER, savedAdmin);
            session.setAttribute(ADMIN, "Y");
            session.setAttribute("pageTitle", "Home");
            response.sendRedirect(request.getContextPath() + "/");
        }

    }
}
