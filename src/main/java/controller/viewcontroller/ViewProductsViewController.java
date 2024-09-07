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

import static utils.RequestAttributeUtil.*;
import static utils.RequestParameterUtil.*;

// /products endpoint with query string parameters represents minPrice, maxPrice, and category
@WebServlet(value = "/products")
public class ViewProductsViewController extends HttpServlet {
    private transient ProductController productController;
    private transient CategoryController categoryController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String minPrice = request.getParameter(MINIMUM_PRICE);
        String maxPrice = request.getParameter(MAXIMUM_PRICE);
        String category = request.getParameter(CATEGORY);
        String name = request.getParameter(NAME);
        Map<String, String> queryParameters = new HashMap<>();
        if (NotEmptyValidator.isValid(minPrice)) {
            queryParameters.put(MINIMUM_PRICE, minPrice);
        }
        if (NotEmptyValidator.isValid(maxPrice)) {
            queryParameters.put(MAXIMUM_PRICE, maxPrice);
        }
        if (NotEmptyValidator.isValid(category)) {
            queryParameters.put(CATEGORY, category);
        }
        if (NotEmptyValidator.isValid(name)) {
            queryParameters.put(NAME, name);
        }
        List<Product> products = productController.search(queryParameters);
        List<Category> categories = categoryController.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("products.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Books");
        request.getSession().setAttribute(PRODUCTS, products);
        request.getSession().setAttribute(CATEGORIES, categories);
        dispatcher.forward(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        productController = new ProductController();
        categoryController = new CategoryController();
    }
}
