package controller.viewcontroller;

import controller.CategoryController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Category;

import java.io.IOException;

import static utils.RequestAttributeUtil.PAGE_TITLE;
import static utils.RequestParameterUtil.CATEGORY;

@WebServlet(value = "/add-category")
public class AddCategoryViewController extends HttpServlet {
    private CategoryController categoryController;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("add-category.jsp");
        request.getSession().setAttribute("PAGE_TITLE", "Add a category");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = (Category) request.getAttribute(CATEGORY);
        Category savedCategory = categoryController.save(category);
        if (savedCategory == null) {
            response.sendRedirect("/add-category");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(PAGE_TITLE, "Home");
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    public void init() {
        categoryController = new CategoryController();
    }
}
