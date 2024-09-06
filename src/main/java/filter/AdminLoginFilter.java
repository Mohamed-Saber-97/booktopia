package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Admin;
import service.AdminService;
import validator.EmailValidator;
import validator.MinFieldLengthValidator;
import validator.NotEmptyValidator;

import java.io.IOException;

@WebFilter(urlPatterns = "/admin-login")
public class AdminLoginFilter implements Filter {
    AdminService adminService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        if (method.equals("GET")) {
            chain.doFilter(request, response);
        } else {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            boolean isValid = true;
            if (!NotEmptyValidator.isValid(email, password)) {
                request.setAttribute("error", "All fields are required");
                isValid = false;
            } else if (!EmailValidator.isValid(email)) {
                request.setAttribute("error", "Invalid email format");
                isValid = false;
            } else if (!MinFieldLengthValidator.isValid(6, password)) {
                request.setAttribute("error", "Password must be at least 6 characters long");
                isValid = false;
            }
            if (isValid) {
                Admin admin = adminService.findByEmail(email);
                if (admin == null || !admin.getAccount().getPassword().equals(password)) {
                    request.setAttribute("error", "Invalid email or password");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin-login.jsp");
                    ((HttpServletRequest) request).getSession().setAttribute("pageTitle", "Admin login");
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("admin", admin);
                    chain.doFilter(request, response);
                }
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin-login.jsp");
                ((HttpServletRequest) request).getSession().setAttribute("pageTitle", "Admin login");
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        adminService = new AdminService();
    }
}
