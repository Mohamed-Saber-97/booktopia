package controller.viewcontroller;

import controller.CategoryController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

import java.io.IOException;

import static utils.RequestParameterUtil.CATEGORY;

@WebServlet(value = "/delete-category")
public class DeleteCategoryViewController extends HttpServlet {
    private CategoryController categoryController;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = (Category) request.getAttribute(CATEGORY);
        category.markAsDeleted();
        categoryController.update(category);
        response.sendRedirect("/categories");
    }

    @Override
    public void init() {
        categoryController = new CategoryController();
    }
}
