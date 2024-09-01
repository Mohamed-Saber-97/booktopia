package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Address;
import model.Buyer;
import model.Category;
import validator.*;

@WebServlet(value = "/signup")
public class SignupController extends HttpServlet {
    BuyerController buyerController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        request.getSession().setAttribute("pageTitle", "Sign up");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Buyer buyer = (Buyer) request.getAttribute("buyer");
        Buyer savedBuyer = buyerController.save(buyer);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", savedBuyer);
        session.setAttribute("pageTitle", "Home");
        System.out.println("Redirecting to home page");
        response.sendRedirect(request.getContextPath() + "/");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        buyerController = new BuyerController();
    }
}