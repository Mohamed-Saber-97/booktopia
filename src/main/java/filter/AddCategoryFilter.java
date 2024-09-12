package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Category;
import utils.RequestBuilderUtil;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.ERROR;
import static utils.RequestAttributeUtil.PAGE_TITLE;
import static utils.RequestParameterUtil.CATEGORY;

@WebFilter(urlPatterns = "/add-category")
public class AddCategoryFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            Map<String, String> errors = ValidatorUtil.validateAddCategory(httpRequest);
            if (!errors.isEmpty()) {
                request.setAttribute(ERROR, errors.get(ERROR));
                RequestDispatcher dispatcher = request.getRequestDispatcher("add-category.jsp");
                httpRequest.getSession().setAttribute(PAGE_TITLE, "Add a category");
                dispatcher.forward(request, response);
            } else {
                Category category = RequestBuilderUtil.createCategoryFromRequest(httpRequest);
                request.setAttribute(CATEGORY, category);
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
