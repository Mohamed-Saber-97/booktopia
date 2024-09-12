package controller.viewcontroller;

import controller.CategoryController;
import controller.ProductController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Category;
import model.Product;
import utils.ImageUtility;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static utils.RequestAttributeUtil.*;
import static utils.RequestParameterUtil.CATEGORY_ID;

@WebServlet(value = "/edit-book")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class UpdateProductViewController extends HttpServlet {
    private CategoryController categoryController;
    private ProductController productController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = (Product) request.getAttribute(PRODUCT);
        List<Category> categories = categoryController.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-book.jsp");
        request.getSession().setAttribute(CATEGORIES, categories);
        request.getSession().setAttribute(PRODUCT, product);
        request.getSession().setAttribute(PAGE_TITLE, product.getName());
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = (Product) request.getAttribute(PRODUCT);
        Category category = categoryController.findById(Long.parseLong(request.getParameter(CATEGORY_ID)));
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
        String imageName = ImageUtility.saveImage(request, uploadPath);
        String oldImagePath = product.getImagePath();
        product.setImagePath("images" + File.separator + imageName);
        product.setCategory(category);
        Product updatedProduct = productController.update(product);
        if (updatedProduct == null) {
            response.sendRedirect("/edit-book");
        } else {
            ImageUtility.deleteImage(getServletContext().getRealPath("") + File.separator + oldImagePath);
            HttpSession session = request.getSession();
            session.setAttribute(PAGE_TITLE, "Home");
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    public void init() {
        categoryController = new CategoryController();
        productController = new ProductController();
    }
}
