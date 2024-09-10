package controller.viewcontroller;

import controller.CategoryController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

import java.io.IOException;
import java.util.List;

import static utils.RequestAttributeUtil.*;

@WebServlet(value = "/books")
public class ProductsViewController extends HttpServlet {
    private CategoryController categoryController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = new SearchProductsController().searchProducts(request);
        List<Category> categories = categoryController.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("books.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Books");
        request.getSession().setAttribute(PRODUCTS, products);
        request.getSession().setAttribute(CATEGORIES, categories);
        dispatcher.forward(request, response);
    }

    @Override
    public void init() {
        categoryController = new CategoryController();
    }
}
