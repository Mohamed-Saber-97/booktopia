package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/signup", "/login", "/admin-login"})
public class GuestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getSession().getAttribute("user") != null) {
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
        } else {
            chain.doFilter(request, response);
        }
    }
}
