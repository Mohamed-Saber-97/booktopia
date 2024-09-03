package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Buyer;
import validator.CountryValidator;

@WebServlet(value = "/signup")
public class SignupController extends HttpServlet {
    BuyerController buyerController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        request.getSession().setAttribute("pageTitle", "Sign up");
        String[] countries = CountryValidator.countryArray;
        request.getSession().setAttribute("countries", countries);
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getAttribute("buyer");
        Buyer savedBuyer = buyerController.save(buyer);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", savedBuyer);
        session.setAttribute("buyer", "Y");
        session.setAttribute("pageTitle", "Home");
        response.sendRedirect(request.getContextPath() + "/");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        buyerController = new BuyerController();
    }
}