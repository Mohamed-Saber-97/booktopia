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

@WebServlet(value = "/products")
public class ViewProductsViewController extends HttpServlet {
    private CategoryController categoryController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = new SearchProductsController().searchProducts(request);
        List<Category> categories = categoryController.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("products.jsp");
        request.getSession().setAttribute("pageTitle", "Books");
        request.getSession().setAttribute("products", products);
        request.getSession().setAttribute("categories", categories);
        dispatcher.forward(request, response);
    }

    @Override
    public void init() {
        categoryController = new CategoryController();
    }
}
