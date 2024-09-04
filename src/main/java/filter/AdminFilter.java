package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter(urlPatterns = "/admin-login")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getSession().getAttribute("admin") == null) {
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
        } else {
            chain.doFilter(request, response);
        }
    }
}
