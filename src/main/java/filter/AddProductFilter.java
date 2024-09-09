package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Product;
import utils.RequestBuilderUtil;
import utils.SaveUploadedImage;
import utils.ValidatorUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.*;

@WebFilter(urlPatterns = "/add-book")
public class AddProductFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            Map<String, String> errors = ValidatorUtil.validateAddBook(httpRequest);
            if (!errors.isEmpty()) {
                request.setAttribute(ERROR, errors.get(ERROR));
                RequestDispatcher dispatcher = request.getRequestDispatcher("add-book.jsp");
                ((HttpServletRequest) request).getSession().setAttribute(PAGE_TITLE, "Add a book");
                dispatcher.forward(request, response);
            } else {
                String uploadPath = httpRequest.getServletContext().getRealPath("") + File.separator + "images";
                Product product = RequestBuilderUtil.createProductFromRequest(httpRequest);
                request.setAttribute(PRODUCT, product);
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
