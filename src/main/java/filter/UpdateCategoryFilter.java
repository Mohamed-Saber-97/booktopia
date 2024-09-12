package filter;

import controller.CategoryController;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import utils.RequestBuilderUtil;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.ERROR;
import static utils.RequestAttributeUtil.PAGE_TITLE;
import static utils.RequestParameterUtil.CATEGORY;
import static utils.RequestParameterUtil.ID;

@WebFilter(urlPatterns = "/edit-category")
public class UpdateCategoryFilter implements Filter {
    private CategoryController categoryController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Map<String, String> errors;
        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            errors = ValidatorUtil.validateEntityId(httpRequest, ID);
            if (!errors.isEmpty()) {
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
            } else {
                Category category = categoryController.findById(Long.parseLong(request.getParameter("id")));
                if (category == null) {
                    httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                    ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
                } else {
                    request.setAttribute(CATEGORY, category);
                    errors = ValidatorUtil.validateUpdateCategory(httpRequest);
                    if (!errors.isEmpty()) {
                        request.setAttribute(ERROR, errors.get(ERROR));
                        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-category.jsp");
                        httpRequest.getSession().setAttribute(PAGE_TITLE, "Edit a category");
                        dispatcher.forward(request, response);
                    } else {
                        category = RequestBuilderUtil.updateCategoryFromRequest(httpRequest);
                        request.setAttribute(CATEGORY, category);
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
                Category category = categoryController.findById(Long.parseLong(request.getParameter("p")));
                if (category == null) {
                    httpRequest.getSession().setAttribute(PAGE_TITLE, "Home");
                    ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/");
                } else {
                    request.setAttribute(CATEGORY, category);
                    chain.doFilter(request, response);
                }
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        categoryController = new CategoryController();
    }
}
