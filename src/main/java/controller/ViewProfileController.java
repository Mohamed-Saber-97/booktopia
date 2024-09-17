package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import validator.CountryValidator;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/profile")
public class ViewProfileController extends HttpServlet {
    private transient CategoryController categoryController;

    @Override
    public void init() {
        categoryController = new CategoryController();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "My Profile");
        request.getSession().setAttribute(COUNTRIES, CountryValidator.getCountries());
        request.getSession().setAttribute(CATEGORIES, categoryController.findAll());
        dispatcher.forward(request, response);
    }
}
