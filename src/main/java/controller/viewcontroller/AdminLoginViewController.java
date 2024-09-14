package controller.viewcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;

import java.io.IOException;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/admin-login")
public class AdminLoginViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin-login.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Admin login");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admin admin = (Admin) request.getAttribute(USER);
        if (admin == null) {
            response.sendRedirect("/admin-login");
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute(USER, admin);
            session.setAttribute(ADMIN, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            request.setAttribute(SUCCESS, "Welcome back, %s".formatted(admin.getAccount().getName()));
            dispatcher.forward(request, response);
        }
    }
}
