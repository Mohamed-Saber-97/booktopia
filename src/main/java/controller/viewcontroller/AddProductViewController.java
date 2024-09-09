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
import utils.SaveUploadedImage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static utils.RequestAttributeUtil.PAGE_TITLE;
import static utils.RequestAttributeUtil.PRODUCT;
import static utils.RequestParameterUtil.CATEGORY_ID;

@WebServlet(value = "/add-book")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AddProductViewController extends HttpServlet {
    private CategoryController categoryController;
    private ProductController productController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryController.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("add-book.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Add a book");
        request.getSession().setAttribute("categories", categories);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
        Product product = (Product) request.getAttribute(PRODUCT);
        if (product == null) {
            response.sendRedirect("/add-book");
        } else {
            String imagePath = SaveUploadedImage.saveImage(request, uploadPath);
            System.out.println("Image path:(inview controller)  " + imagePath);
            product.setImagePath("images" + File.separator + imagePath);
            Category category = categoryController.findById(Long.parseLong(request.getParameter(CATEGORY_ID)));
            product.setCategory(category);
            productController.save(product);
            HttpSession session = request.getSession(true);
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
