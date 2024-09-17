package validator;

import controller.CategoryController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

import java.io.IOException;

import static utils.RequestParameterUtil.NAME;

@WebServlet("/update-category-name")
public class UpdateCategoryNameValidator extends HttpServlet {
    public static final String ERROR_MESSAGE = "Name already exists";
    public static final String ERROR_MESSAGE_LENGTH = "Name must be at most 100 characters";
    private static final CategoryController categoryController = new CategoryController();

    public static boolean isValid(HttpServletRequest request) {
        String category = request.getParameter(NAME);
        Category entity = new CategoryController().findByName(category);
        return entity == null && category.length() <= 100;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter(NAME);
        String responseText;
        if (category.length() > 100) {
            responseText = ERROR_MESSAGE_LENGTH; // Name is too short
        } else if (!UpdateCategoryNameValidator.isValid(request)) {
            responseText = ERROR_MESSAGE; // Name already exists
        } else {
            responseText = "true"; // Name is valid
        }
        response.setContentType("text/plain");
        response.getWriter().write(responseText);
    }
}
