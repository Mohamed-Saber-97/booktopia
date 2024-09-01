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
        request.setAttribute("pageTitle", "Sign up");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String creditLimit = request.getParameter("creditLimit");
        String phoneNumber = request.getParameter("phoneNumber");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String zipcode = request.getParameter("zipcode");

        Buyer buyer = new Buyer();
        buyer.setAccount(new Account());
        buyer.getAccount().setAddress(new Address());
        buyer.getAccount().setName(name);
        buyer.getAccount().setBirthday(LocalDate.parse(birthday));
        buyer.getAccount().setJob(job);
        buyer.getAccount().setEmail(email);
        buyer.getAccount().setPassword(password);
        buyer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(creditLimit)));
        buyer.getAccount().setPhoneNumber(phoneNumber);
        buyer.getAccount().getAddress().setCountry(country);
        buyer.getAccount().getAddress().setCity(city);
        buyer.getAccount().getAddress().setStreet(street);
        buyer.getAccount().getAddress().setZipcode(zipcode);

        Buyer savedBuyer = buyerController.save(buyer);

        HttpSession session = request.getSession(true);
        session.setAttribute("user", savedBuyer);
        request.setAttribute("pageTitle", "Home");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        buyerController = new BuyerController();
    }
}