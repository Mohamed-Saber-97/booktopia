package org.example.booktopia.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

@Controller
public class ImageUtility {

    public String saveImage(HttpServletRequest request, String uploadPath) throws ServletException, IOException {
        File uploadDir = new File(uploadPath);

        Part filePart = request.getPart("imagePath");

        String fileName = filePart.hashCode() + "-" + System.currentTimeMillis() + filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf("."));
//        String imagePath = fileName.substring(fileName.lastIndexOf(File.separator) + 1);

        // Write the file to the server directory
        filePart.write(uploadPath + File.separator + fileName);

        return fileName;
    }

    public void deleteImage(String imagePath) {
        File file = new File(imagePath);
        if (file.delete()) {
            System.out.println("File " + imagePath + " deleted successfully");
        } else {
            System.out.println("Failed to delete the file " + imagePath);
        }
    }
}
