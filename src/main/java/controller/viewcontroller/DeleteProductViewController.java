package controller.viewcontroller;

import controller.ProductController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

import java.io.IOException;

import static utils.RequestAttributeUtil.PRODUCT;

@WebServlet(value = "/delete-book")
public class DeleteProductViewController extends HttpServlet {
    private ProductController productController;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = (Product) request.getAttribute(PRODUCT);
        product.markAsDeleted();
        productController.update(product);
        response.sendRedirect("/books");
    }

    @Override
    public void init() {
        productController = new ProductController();
    }
}
