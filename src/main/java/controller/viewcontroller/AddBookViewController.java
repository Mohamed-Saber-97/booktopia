package controller.viewcontroller;

import com.mysql.cj.protocol.a.authentication.Sha256PasswordPlugin;
import controller.CategoryController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Category;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static utils.RequestAttributeUtil.PAGE_TITLE;

@WebServlet(value = "/add-book")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AddBookViewController extends HttpServlet {
    private CategoryController categoryController;

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
        System.out.println("Upload path: " + uploadPath);

        File uploadDir = new File(uploadPath);
        System.out.println("Upload dir: " + uploadDir);

        Part filePart = request.getPart("imagePath");
        System.out.println("File part: " + filePart);

        String fileName = filePart.hashCode() + "-" + System.currentTimeMillis() + filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf("."));
        System.out.println("File name: " + fileName);
        String imagePath = fileName.substring(fileName.lastIndexOf(File.separator) + 1);

        // Write the file to the server directory
        filePart.write(uploadPath + File.separator + fileName);

        // Response to the client
        response.getWriter().println("File " + fileName + " uploaded successfully to " + uploadPath);
        System.out.println("File " + fileName + " uploaded successfully to " + uploadPath);
    }

    @Override
    public void init() {
        categoryController = new CategoryController();
    }
}
