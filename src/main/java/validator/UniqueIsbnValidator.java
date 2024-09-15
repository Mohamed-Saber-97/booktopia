package validator;

import controller.ProductController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/ISBN-Validator")
public class UniqueIsbnValidator extends HttpServlet{
    public static final String ERROR_MESSAGE = "ISBN already exists";
    public static final ProductController productController = new ProductController();

    public static boolean isValid(String... isbns) {
        return Arrays.stream(isbns).noneMatch(productController::existsByIsbn);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _ISBN = request.getParameter("isbn");
        String responseText = isValid(_ISBN) ? "true" : ERROR_MESSAGE;
        response.setContentType("text/plain");
        response.getWriter().write(responseText);
    }
}
