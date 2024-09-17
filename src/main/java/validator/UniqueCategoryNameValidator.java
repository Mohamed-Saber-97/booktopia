package validator;

import controller.CategoryController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

import static utils.RequestParameterUtil.CATEGORY;
import static utils.RequestParameterUtil.NAME;

@WebServlet("/unique-category-name")
public class UniqueCategoryNameValidator extends HttpServlet {
    public static final String ERROR_MESSAGE = "Name already exists";
    public static final String ERROR_MESSAGE_LENGTH = "Name must be at most 100 characters";
    private static final CategoryController categoryController = new CategoryController();

    public static boolean isValid(String name) {
        if (name.length() > 100) {
            return false; // Length validation fails
        }
        return !categoryController.existsByName(name);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter(NAME);
        String responseText;
        if (category.length() > 100) {
            responseText = ERROR_MESSAGE_LENGTH; // Name is too short
        } else if (!UniqueCategoryNameValidator.isValid(category)) {
            responseText = ERROR_MESSAGE; // Name already exists
        } else {
            responseText = "true"; // Name is valid
        }
        response.setContentType("text/plain");
        response.getWriter().write(responseText);
    }
}
