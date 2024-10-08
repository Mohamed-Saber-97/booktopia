package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static utils.RequestAttributeUtil.ADMIN;

@WebFilter(urlPatterns = {"/books", "/add-book", "/edit-book", "/delete-book", "/categories", "/add-category", "/edit-category", "/delete-category", "/buyers", "/buyer-profile", "/buyer-orders", "/next-buyer-orders", "/buyer-order-products"})
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getSession().getAttribute(ADMIN) == null) {
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
        } else {
            chain.doFilter(request, response);
        }
    }
}
