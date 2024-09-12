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

@WebServlet(value = "/edit-category")
public class UpdateCategoryViewController extends HttpServlet {
    private CategoryController categoryController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = (Category) request.getAttribute(CATEGORY);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-category.jsp");
        request.getSession().setAttribute(CATEGORY, category);
        request.getSession().setAttribute(PAGE_TITLE, category.getName());
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = (Category) request.getAttribute(CATEGORY);
        Category updatedCategory = categoryController.update(category);
        if (updatedCategory == null) {
            response.sendRedirect("/edit-category");
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
