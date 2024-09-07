package controller.viewcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static utils.RequestAttributeUtil.PAGE_TITLE;

@WebServlet(value = "/cart")
public class CartViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Cart");
        dispatcher.forward(request, response);
    }
}
