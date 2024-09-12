package validator;

import java.util.Arrays;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/validateEmail")
public class EmailValidator extends HttpServlet{
    public static final String ERROR_MESSAGE = "Invalid email";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/plain");
        String email = request.getParameter("email");

        String status;

        if (isValid(email)) {
            status = "ture";
        } else {
            status = "false";
        }
        
        response.setContentType("text/plain");
        response.getWriter().write(status);

    }
    

    public static boolean isValid(String... emails) {
        return Arrays.stream(emails).allMatch(email -> email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"));
    }
}