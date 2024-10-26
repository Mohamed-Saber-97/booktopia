package org.example.booktopia.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.booktopia.controller.CategoryController;
import org.example.booktopia.controller.ProductController;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.NewProductDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.service.ProductService;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class RequestBuilderUtil {
    private final CategoryController categoryController;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final ServletContext servletContext;
    private final ImageUtility imageUtility;

    private void setCommonProductAttributes(NewProductDto productDto, Product product) {
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());
        product.setReleaseDate(productDto.releaseDate());
        product.setIsbn(productDto.isbn());
        product.setAuthor(productDto.author());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
    }

    public void createProductFromRequest(HttpServletRequest request, NewProductDto productDto) throws ServletException, IOException {
        Product product = new Product();
        setCommonProductAttributes(productDto, product);

        String uploadPath = servletContext.getRealPath("") + File.separator + "images";
        System.out.println("uploadPath: " + uploadPath);
        String imageName = imageUtility.saveImage(request, uploadPath);
        System.out.println("imageName: " + imageName);
        product.setImagePath("images" + File.separator + imageName);
        CategoryDto categoryDto = categoryService.findById(productDto.categoryId());
        Category category = categoryMapper.toEntity(categoryDto);
        product.setCategory(category);
        System.out.println("My category" + category.getId());
        productService.save(product);
    }
}
