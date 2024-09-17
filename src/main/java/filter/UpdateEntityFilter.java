package filter;

import controller.CategoryController;
import controller.ProductController;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;
import utils.RequestBuilderUtil;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.*;
import static utils.RequestParameterUtil.CATEGORY;
import static utils.RequestParameterUtil.ID;

@WebFilter(urlPatterns = {"/edit-book", "/edit-category"})
public class UpdateEntityFilter implements Filter {
    private ProductController productController;
    private CategoryController categoryController;
    private Product product;
    private Category category;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getServletPath();
        RequestDispatcher dispatcher;
        Map<String, String> errors;
        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            errors = ValidatorUtil.validateEntityId(httpRequest, ID);
            if (!errors.isEmpty()) {
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
            } else {
                if (path.equals("/edit-book")) {
                    product = productController.findById(Long.parseLong(request.getParameter("id")));
                } else {
                    category = categoryController.findById(Long.parseLong(request.getParameter("id")));
                }
                if (product == null && path.equals("/edit-book") || category == null && path.equals("/edit-category")) {
                    httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                    ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
                } else {
                    if (path.equals("/edit-book")) {
                        request.setAttribute(PRODUCT, product);
                        errors = ValidatorUtil.validateUpdateBook(httpRequest);

                    } else {
                        request.setAttribute(CATEGORY, category);
                        errors = ValidatorUtil.validateUpdateCategory(httpRequest);
                    }
                    if (!errors.isEmpty()) {
                        request.setAttribute(ERROR, errors.get(ERROR));
                        if (path.equals("/edit-book")) {
                            dispatcher = request.getRequestDispatcher("edit-book.jsp");
                            httpRequest.getSession().setAttribute(PAGE_TITLE, "Edit a book");
                        } else {
                            dispatcher = request.getRequestDispatcher("edit-category.jsp");
                            httpRequest.getSession().setAttribute(PAGE_TITLE, "Edit a category");
                        }
                        dispatcher.forward(request, response);
                    } else {
                        if (path.equals("/edit-book")) {
                            product = RequestBuilderUtil.updateProductFromRequest(httpRequest);
                            request.setAttribute(PRODUCT, product);
                        } else {
                            category = RequestBuilderUtil.updateCategoryFromRequest(httpRequest);
                            request.setAttribute(CATEGORY, category);
                        }
                        chain.doFilter(request, response);
                    }
                }
            }
        } else {
            errors = ValidatorUtil.validateEntityId(httpRequest, "p");
            if (!errors.isEmpty()) {
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
            } else {
                if (path.equals("/edit-book")) {
                    product = productController.findById(Long.parseLong(request.getParameter("p")));
                } else {
                    category = categoryController.findById(Long.parseLong(request.getParameter("p")));
                }
                if (product == null && path.equals("/edit-book") || category == null && path.equals("/edit-category")) {
                    httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                    ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
                } else {
                    if (path.equals("/edit-book")) {
                        request.setAttribute(PRODUCT, product);
                    } else {
                        request.setAttribute(CATEGORY, category);
                    }
                    chain.doFilter(request, response);
                }
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        productController = new ProductController();
        categoryController = new CategoryController();
    }
}
