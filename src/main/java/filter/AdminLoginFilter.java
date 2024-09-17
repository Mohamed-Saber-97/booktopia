package filter;

import controller.AdminController;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Admin;
import utils.RequestAttributeUtil;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.*;
import static utils.RequestParameterUtil.EMAIL;
import static utils.RequestParameterUtil.PASSWORD;

@WebFilter(urlPatterns = "/admin-login")
public class AdminLoginFilter implements Filter {
    private AdminController adminController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        adminController = new AdminController();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            Map<String, String> errors = ValidatorUtil.validateAdminLogin(httpRequest);

            if (errors.isEmpty()) {
                String email = request.getParameter(EMAIL);
                String password = request.getParameter(PASSWORD);
                if (adminController.login(email, password)) {
                    Admin admin = adminController.findByEmail(email);
                    request.setAttribute(USER, admin);
                    request.setAttribute(ADMIN, YES);
                    chain.doFilter(request, response);
                } else {
                    request.setAttribute(ERROR, "Invalid email or password");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin-login.jsp");
                    ((HttpServletRequest) request).getSession().setAttribute(PAGE_TITLE, "Admin login");
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute(ERROR, errors.get(RequestAttributeUtil.ERROR));
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin-login.jsp");
                ((HttpServletRequest) request).getSession().setAttribute(PAGE_TITLE, "Admin Login");
                dispatcher.forward(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
