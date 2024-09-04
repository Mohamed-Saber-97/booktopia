package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Buyer;
import utils.RequestBuilderUtil;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/signup")
public class SignupFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            Map<String, String> errors = ValidatorUtil.validateSignup(httpRequest);
            if (!errors.isEmpty()) {
                request.setAttribute("error", errors.get("error"));
                RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
                ((HttpServletRequest) request).getSession().setAttribute("pageTitle", "Sign up");
                dispatcher.forward(request, response);
            } else {
                Buyer buyer = RequestBuilderUtil.buildBuyerFromRequest(httpRequest);
                request.setAttribute("buyer", buyer);
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
