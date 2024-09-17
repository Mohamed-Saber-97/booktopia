package validator;

import controller.ProductController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

import static utils.RequestParameterUtil.ISBN;

@WebServlet("/unique-isbn")
public class UniqueIsbnValidator extends HttpServlet {
    public static final String ERROR_MESSAGE = "ISBN already exists";
    public static final String ERROR_MESSAGE_LENGTH = "Invalid ISBN length";
    public static final String VALID_MESSAGE = "true";
    public static final ProductController productController = new ProductController();

    public UniqueIsbnValidator() {
    }

    public static boolean isValidLength(String isbn) {
        return isbn.length() == 10 || isbn.length() == 13; // Accept only 10 or 13 digits
    }

    public static boolean isValid(String... isbns) {
        return Arrays.stream(isbns).noneMatch(productController::existsByIsbn);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter(ISBN);
        String responseText;
        if (!isValidLength(isbn)) {
            responseText = ERROR_MESSAGE_LENGTH;
        } else if (!isValid(isbn)) {
            responseText = ERROR_MESSAGE;
        } else {
            responseText = VALID_MESSAGE;
        }
        response.setContentType("text/plain");
        response.getWriter().write(responseText);
    }
}
