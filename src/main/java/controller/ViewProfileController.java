package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import validator.CountryValidator;

import java.io.IOException;

@WebServlet(value = "/profile")
public class ViewProfileController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        request.getSession().setAttribute("pageTitle", "My Profile");
        String[] countries = CountryValidator.countryArray;
        request.getSession().setAttribute("countries", countries);
        dispatcher.forward(request, response);
    }
}
