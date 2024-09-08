package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static utils.RequestAttributeUtil.BUYER;

@WebFilter(urlPatterns = { "/orders", "/addToCart", "/cart", "/update-cart", "/remove-cart-item",
        "/cartSingleOperation", "/addToWishlist", "/wishlist", "/remove-wishlist-item" })
public class BuyerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getSession().getAttribute(BUYER) == null) {
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
        } else {
            chain.doFilter(request, response);
        }
    }
}
