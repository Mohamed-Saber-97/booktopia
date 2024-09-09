package controller.viewcontroller;

import controller.ProductController;
import jakarta.servlet.http.HttpServletRequest;
import model.Product;
import validator.NotEmptyValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductsController {
    private ProductController productController;

    public List<Product> searchProducts(HttpServletRequest request) {
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String category = request.getParameter("category");
        String pageNumberString = request.getParameter("page");
        int pageNumber = NotEmptyValidator.isValid(pageNumberString) ? Integer.parseInt(pageNumberString) : 0;
        System.out.println("Page number:hhh " + pageNumber);
        System.out.println("Page number: " + pageNumberString);
        System.out.println("Min price: " + minPrice);
        System.out.println("Max price: " + maxPrice);
        System.out.println("Category: " + category);
        String name = request.getParameter("name");
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
        if (NotEmptyValidator.isValid(name)) {
            queryParameters.put("name", name);
        }
        return productController.search(queryParameters, pageNumber, 16);
    }

    public SearchProductsController() {
        productController = new ProductController();
    }
}
