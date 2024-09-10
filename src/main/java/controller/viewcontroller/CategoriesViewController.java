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

import static utils.RequestAttributeUtil.CATEGORIES;
import static utils.RequestAttributeUtil.PAGE_TITLE;

@WebServlet(value = "/categories")
public class CategoriesViewController extends HttpServlet {
    private CategoryController categoryController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryController.findAll();
        int i = 1;
        RequestDispatcher dispatcher = request.getRequestDispatcher("categories.jsp");
        request.setAttribute(PAGE_TITLE, "Categories");
        request.setAttribute(CATEGORIES, categories);
        request.setAttribute("i", i);
        dispatcher.forward(request, response);
    }

    @Override
    public void init() {
        categoryController = new CategoryController();
    }
}
