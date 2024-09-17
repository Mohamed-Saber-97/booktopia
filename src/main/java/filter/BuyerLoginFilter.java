package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Buyer;
import service.BuyerService;
import utils.RequestAttributeUtil;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.*;
import static utils.RequestParameterUtil.EMAIL;
import static utils.RequestParameterUtil.PASSWORD;

@WebFilter(urlPatterns = "/login")
public class BuyerLoginFilter implements Filter {
    private BuyerService buyerService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        buyerService = new BuyerService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            Map<String, String> errors = ValidatorUtil.validateBuyerLogin(httpRequest);

            if (errors.isEmpty()) {
                String email = request.getParameter(EMAIL);
                String password = request.getParameter(PASSWORD);
                if (buyerService.checkValidLoginCredentials(email, password)) {
                    Buyer buyer = buyerService.findByEmail(email);
                    request.setAttribute(USER, buyer);
                    request.setAttribute(BUYER, YES);
                    chain.doFilter(request, response);
                } else {
                    request.setAttribute(ERROR, "Invalid email or password");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("buyer-login.jsp");
                    ((HttpServletRequest) request).getSession().setAttribute(PAGE_TITLE, "Buyer login");
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute(ERROR, errors.get(RequestAttributeUtil.ERROR));
                RequestDispatcher dispatcher = request.getRequestDispatcher("buyer-login.jsp");
                ((HttpServletRequest) request).getSession().setAttribute(PAGE_TITLE, "Buyer Login");
                dispatcher.forward(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
