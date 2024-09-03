package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        if (request.getSession().getAttribute("buyer") != null) {
            request.getSession().setAttribute("creditLimit", "Y");
        }
        dispatcher.forward(request, response);
    }
}
