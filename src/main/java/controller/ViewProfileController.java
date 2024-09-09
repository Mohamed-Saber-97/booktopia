package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import validator.CountryValidator;

import java.io.IOException;

import static utils.RequestAttributeUtil.COUNTRIES;
import static utils.RequestAttributeUtil.PAGE_TITLE;

@WebServlet(value = "/profile")
public class ViewProfileController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "My Profile");
        request.getSession().setAttribute(COUNTRIES, CountryValidator.getCountries());
        dispatcher.forward(request, response);
    }
}
