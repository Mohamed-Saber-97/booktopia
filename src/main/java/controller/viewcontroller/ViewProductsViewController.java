package controller.viewcontroller;

import controller.CategoryController;
import controller.ProductController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;
import validator.NotEmptyValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// /products endpoint with query string parameters represents minPrice, maxPrice, and category
@WebServlet(value = "/products")
public class ViewProductsViewController extends HttpServlet {
    private ProductController productController;
    private CategoryController categoryController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String category = request.getParameter("category");
        Map<String, String> queryParameters = new HashMap<>();
        if (NotEmptyValidator.isValid(minPrice)) {
            queryParameters.put("minPrice", minPrice);
        }
        if (NotEmptyValidator.isValid(maxPrice)) {
            queryParameters.put("maxPrice", maxPrice);
        }
        if (NotEmptyValidator.isValid(category)) {
            queryParameters.put("category", category);
        }
        List<Product> products = productController.search(queryParameters);
        List<Category> categories = categoryController.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("products.jsp");
        request.getSession().setAttribute("pageTitle", "Books");
        request.getSession().setAttribute("products", products);
        request.getSession().setAttribute("categories", categories);
        dispatcher.forward(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        productController = new ProductController();
        categoryController = new CategoryController();
    }
}
