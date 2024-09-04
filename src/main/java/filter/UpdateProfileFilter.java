package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Admin;
import model.Buyer;
import utils.RequestBuilderUtil;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/update-profile")
public class UpdateProfileFilter implements Filter {
    private static final String ERROR = "error";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            // Determine if it's a Buyer or Admin update based on session attributes
            boolean isBuyer = httpRequest.getSession().getAttribute("buyer") != null;
            boolean isAdmin = httpRequest.getSession().getAttribute("admin") != null;

            Map<String, String> errors;
            if (isBuyer) {
                errors = ValidatorUtil.validateBuyerUpdateProfile(httpRequest);
            } else if (isAdmin) {
                errors = ValidatorUtil.validateAdminUpdateProfile(httpRequest);
            } else {
                request.setAttribute(ERROR, "Unauthorized user");
                httpRequest.getRequestDispatcher("signup.jsp").forward(request, response);
                return;
            }

            if (!errors.isEmpty()) {
                request.setAttribute(ERROR, errors.get(ERROR));
                RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
                httpRequest.getSession().setAttribute("pageTitle", "Update Profile");
                dispatcher.forward(request, response);
            } else {
                if (isBuyer) {
                    Buyer buyer = RequestBuilderUtil.buildBuyerFromRequest(httpRequest);
                    request.setAttribute("buyer", buyer);
                } else {
                    Admin admin = RequestBuilderUtil.buildAdminFromRequest(httpRequest);
                    request.setAttribute("admin", admin);
                }
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
