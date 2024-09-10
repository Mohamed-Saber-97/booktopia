package filter;

import controller.CategoryController;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.ERROR;
import static utils.RequestAttributeUtil.PAGE_TITLE;
import static utils.RequestParameterUtil.CATEGORY;
import static utils.RequestParameterUtil.ID;

@WebFilter(urlPatterns = "/delete-category")
public class DeleteCategoryFilter implements Filter {
    private CategoryController categoryController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        if ("POST".equalsIgnoreCase(method)) {
            Map<String, String> errors = ValidatorUtil.validateEntityIdPOST(httpRequest);
            if (!errors.isEmpty()) {
                request.setAttribute(ERROR, errors.get(ERROR));
                RequestDispatcher dispatcher = request.getRequestDispatcher("categories.jsp");
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Categories");
                dispatcher.forward(request, response);
            } else {
                Category category = categoryController.findById(Long.parseLong(httpRequest.getParameter(ID)));
                if (category != null) {
                    request.setAttribute(CATEGORY, category);
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
        categoryController = new CategoryController();
    }
}
