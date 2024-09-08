package controller.viewcontroller;

import controller.CategoryController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

import java.io.IOException;
import java.util.List;

import static utils.RequestAttributeUtil.PAGE_TITLE;

@WebServlet(value = "/add-book")
public class AddBookViewController extends HttpServlet {
    private CategoryController categoryController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryController.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("add-book.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Add a book");
        request.getSession().setAttribute("categories", categories);
        dispatcher.forward(request, response);
    }

    @Override
    public void init() {
        categoryController = new CategoryController();
    }
}
