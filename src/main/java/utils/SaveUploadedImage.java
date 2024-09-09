package utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

public class SaveUploadedImage {
    private SaveUploadedImage() {
    }

    public static String saveImage(HttpServletRequest request, String uploadPath) throws ServletException, IOException {
        File uploadDir = new File(uploadPath);
        System.out.println("Upload dir: " + uploadDir);

        Part filePart = request.getPart("imagePath");
        System.out.println("File part: " + filePart);

        String fileName = filePart.hashCode() + "-" + System.currentTimeMillis() + filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf("."));
        System.out.println("File name: " + fileName);
//        String imagePath = fileName.substring(fileName.lastIndexOf(File.separator) + 1);

        // Write the file to the server directory
        filePart.write(uploadPath + File.separator + fileName);

        System.out.println("File " + fileName + " uploaded successfully to " + uploadPath);
        return fileName;
    }
}
