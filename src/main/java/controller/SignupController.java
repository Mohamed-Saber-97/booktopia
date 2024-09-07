package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Buyer;
import validator.CountryValidator;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/signup")
public class SignupController extends HttpServlet {
    private transient BuyerController buyerController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Sign up");
        request.getSession().setAttribute(COUNTRIES, CountryValidator.getCountries());
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getAttribute(USER);
        Buyer savedBuyer = buyerController.save(buyer);
        if (savedBuyer == null) {
            response.sendRedirect("/signup");
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute(USER, savedBuyer);
            session.setAttribute(BUYER, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        buyerController = new BuyerController();
    }
}