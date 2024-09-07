package controller.viewcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;
import static utils.RequestAttributeUtil.*;

import java.io.IOException;

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
        HttpSession session = request.getSession(true);
        session.setAttribute(USER, admin);
        session.setAttribute(ADMIN, YES);
        session.setAttribute(PAGE_TITLE, "Home");
        response.sendRedirect(request.getContextPath() + "/");
    }
}
