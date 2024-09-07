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
import utils.RequestBuilderUtil;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/update-profile")
public class UpdateProfileController extends HttpServlet {

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
        boolean isBuyer = request.getSession().getAttribute(BUYER) != null;
        boolean isAdmin = request.getSession().getAttribute(ADMIN) != null;
        if (isBuyer) {
            Buyer buyer = RequestBuilderUtil.updateBuyerFromRequest(request);
            Buyer savedBuyer = buyerController.update(buyer);
            HttpSession session = request.getSession();
            session.setAttribute(USER, savedBuyer);
            session.setAttribute(BUYER, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            response.sendRedirect(request.getContextPath() + "/");
        } else if (isAdmin) {
            Admin admin = RequestBuilderUtil.updateAdminFromRequest(request);
            Admin savedAdmin = adminController.update(admin);
            HttpSession session = request.getSession();
            session.setAttribute(USER, savedAdmin);
            session.setAttribute(ADMIN, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            response.sendRedirect(request.getContextPath() + "/");
        }

    }
}
