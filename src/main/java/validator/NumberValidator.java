package validator;

import java.util.Arrays;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/validateNumber")
public class NumberValidator extends HttpServlet {
    public static final String ERROR_MESSAGE = "Invalid number";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/plain");
        String phone = request.getParameter("phone");

        String status;

        if (isValid(phone)) {
            status = "ture";
        } else {
            status = "false";
        }
        
        response.setContentType("text/plain");
        response.getWriter().write(status);

    }
    

    public static boolean isValid(String... numbers) {
        return Arrays.stream(numbers).allMatch(number -> number.matches("^\\d+$"));
    }
}
