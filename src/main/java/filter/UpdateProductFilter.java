package filter;

import controller.ProductController;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import model.Product;
import utils.RequestBuilderUtil;
import utils.ValidatorUtil;
import validator.NotEmptyValidator;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.*;

@WebFilter(urlPatterns = "/edit-book")
public class UpdateProductFilter implements Filter {
    private ProductController productController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Map<String, String> errors;
        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            errors = ValidatorUtil.validateEntityIdPOST(httpRequest);
            if (!errors.isEmpty()) {
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
            } else {
                Product product = productController.findById(Long.parseLong(request.getParameter("id")));
                if (product == null) {
                    httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                    ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
                } else {
                    request.setAttribute(PRODUCT, product);
                    errors = ValidatorUtil.validateUpdateBook(httpRequest);
                    if (!errors.isEmpty()) {
                        request.setAttribute(ERROR, errors.get(ERROR));
                        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-book.jsp");
                        httpRequest.getSession().setAttribute(PAGE_TITLE, "Edit a book");
                        dispatcher.forward(request, response);
                    } else {
                        product = RequestBuilderUtil.updateProductFromRequest(httpRequest);
                        request.setAttribute(PRODUCT, product);
                        chain.doFilter(request, response);
                    }
                }
            }
        } else {
            errors = ValidatorUtil.validateEntityIdGET(httpRequest);
            if (!errors.isEmpty()) {
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
            } else {
                Product product = productController.findById(Long.parseLong(request.getParameter("p")));
                if (product == null) {
                    httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                    ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
                } else {
                    request.setAttribute(PRODUCT, product);
                    chain.doFilter(request, response);
                }
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        productController = new ProductController();
    }
}
