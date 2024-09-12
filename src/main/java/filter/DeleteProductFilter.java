package filter;

import controller.ProductController;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.*;
import static utils.RequestParameterUtil.ID;

@WebFilter(urlPatterns = "/delete-book")
public class DeleteProductFilter implements Filter {
    private ProductController productController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        if ("POST".equalsIgnoreCase(method)) {
            Map<String, String> errors = ValidatorUtil.validateEntityId(httpRequest, ID);
            if (!errors.isEmpty()) {
                request.setAttribute(ERROR, errors.get(ERROR));
                RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Books");
                dispatcher.forward(request, response);
            } else {
                Product product = productController.findById(Long.parseLong(httpRequest.getParameter(ID)));
                if (product != null) {
                    request.setAttribute(PRODUCT, product);
                    chain.doFilter(request, response);
                } else {
                    ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
                }
            }
        } else {
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        productController = new ProductController();
    }
}
