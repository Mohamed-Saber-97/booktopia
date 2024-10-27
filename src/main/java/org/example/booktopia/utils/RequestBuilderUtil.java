package org.example.booktopia.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.booktopia.controller.CategoryController;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.NewProductDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.mapper.NewProductMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.service.ProductService;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

import static org.example.booktopia.utils.RequestAttributeUtil.PAGE_TITLE;
import static org.example.booktopia.utils.RequestAttributeUtil.PRODUCT;

@Controller
@AllArgsConstructor
public class RequestBuilderUtil {
    private final CategoryController categoryController;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final NewProductMapper newProductMapper;
    private final ServletContext servletContext;
    private final ImageUtility imageUtility;

    private void setCommonProductAttributes(Product product, Product newProduct) {
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());
        newProduct.setReleaseDate(product.getReleaseDate());
        newProduct.setIsbn(product.getIsbn());
        newProduct.setAuthor(product.getAuthor());
        newProduct.setName(product.getName());
        newProduct.setDescription(product.getDescription());
    }

    public void createProductFromRequest(HttpServletRequest request, NewProductDto productDto) throws ServletException, IOException {
        Product newProduct = new Product();
        Product product = newProductMapper.toEntity(productDto);
        setCommonProductAttributes(product, newProduct);

        String uploadPath = servletContext.getRealPath("") + File.separator + "images";
        System.out.println("uploadPath: " + uploadPath);
        String imageName = imageUtility.saveImage(request, uploadPath);
        System.out.println("imageName: " + imageName);
        newProduct.setImagePath("images" + File.separator + imageName);
        CategoryDto categoryDto = categoryService.findById(productDto.categoryId());
        Category category = categoryMapper.toEntity(categoryDto);
        newProduct.setCategory(category);
        System.out.println("My category" + category.getId());
        productService.save(newProduct);
    }

    public void updateProductFromRequest(HttpServletRequest request, ProductDto productDto) throws ServletException, IOException {
        Product newProduct = new Product();
        Product product = productMapper.toEntity(productDto);
        setCommonProductAttributes(product, newProduct);


        Long categoryId = Long.parseLong(request.getParameter("categoryId"));
        System.out.println("My category iddd: " + categoryId);
        CategoryDto categoryDto = categoryService.findById(categoryId);
        newProduct.setCategory(categoryMapper.toEntity(categoryDto));
        newProduct.setId(Long.parseLong(request.getParameter("id")));
        System.out.println("My product id: " + newProduct.getId());

        String uploadPath = servletContext.getRealPath("") + File.separator + "images";
        String imageName = imageUtility.saveImage(request, uploadPath);
        String oldImagePath = productService.findProductById(newProduct.getId()).imagePath();
        System.out.println("oldImagePath: " + oldImagePath);

        newProduct.setImagePath("images" + File.separator + imageName);
        Category category = categoryMapper.toEntity(categoryDto);
        newProduct.setCategory(category);
        productService.save(newProduct);

        imageUtility.deleteImage(servletContext.getRealPath("") + File.separator + oldImagePath);
    }
}
