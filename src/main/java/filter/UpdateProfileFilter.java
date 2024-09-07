package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Admin;
import model.Buyer;
import static utils.RequestAttributeUtil.*;
import utils.RequestBuilderUtil;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebFilter(urlPatterns = "/update-profile")
public class UpdateProfileFilter implements Filter {

    private Map<String, String> errors = HashMap.newHashMap(0);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            // Determine if it's a Buyer or Admin update based on session attributes
            boolean isBuyer = httpRequest.getSession().getAttribute(BUYER) != null;
            boolean isAdmin = httpRequest.getSession().getAttribute(ADMIN) != null;
            if (!isBuyer && !isAdmin) {
                request.setAttribute(ERROR, "Unauthorized user");
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Signup");
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            } else if (isBuyer) {
                Buyer buyer = RequestBuilderUtil.updateBuyerFromRequest(request);
                httpRequest.getSession().setAttribute(USER, buyer);
                httpRequest.getSession().setAttribute(BUYER, YES);
                errors = ValidatorUtil.validateBuyerUpdateProfile(request);
            } else if(isAdmin){
                Admin admin = RequestBuilderUtil.updateAdminFromRequest(request);
                httpRequest.getSession().setAttribute(USER, admin);
                httpRequest.getSession().setAttribute(ADMIN, YES);
                errors = ValidatorUtil.validateAdminUpdateProfile(request);
            }

            if (!errors.isEmpty()) {
                request.setAttribute(ERROR, errors.get(ERROR));
                RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Update Profile");
                dispatcher.forward(request, response);
            } else {
                httpRequest.getSession().setAttribute(PAGE_TITLE, "My Profile");
                if (isBuyer) {
                    Buyer buyer = RequestBuilderUtil.updateBuyerFromRequest(request);
                    request.setAttribute(USER, buyer);
                    request.setAttribute(BUYER, YES);
                } else if(isAdmin) {
                    Admin admin = RequestBuilderUtil.updateAdminFromRequest(request);
                    request.setAttribute(USER, admin);
                    request.setAttribute(ADMIN, YES);
                }
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
