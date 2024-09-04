package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Account;
import model.Address;
import model.Buyer;
import utils.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
                Buyer buyer = new Buyer();
                buyer.setAccount(new Account());
                buyer.getAccount().setAddress(new Address());
                buyer.getAccount().setName(request.getParameter("name"));
                buyer.getAccount().setBirthday(LocalDate.parse(request.getParameter("birthday")));
                buyer.getAccount().setJob(request.getParameter("job"));
                buyer.getAccount().setEmail(request.getParameter("email"));
                buyer.getAccount().setPassword(request.getParameter("password"));
                buyer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(request.getParameter("creditLimit"))));
                buyer.getAccount().setPhoneNumber(request.getParameter("phoneNumber"));
                buyer.getAccount().getAddress().setCountry(request.getParameter("country"));
                buyer.getAccount().getAddress().setCity(request.getParameter("city"));
                buyer.getAccount().getAddress().setStreet(request.getParameter("street"));
                buyer.getAccount().getAddress().setZipcode(request.getParameter("zipcode"));
                request.setAttribute("buyer", buyer);
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
