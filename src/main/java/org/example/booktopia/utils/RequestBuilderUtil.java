package org.example.booktopia.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.controller.CategoryController;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.NewProductDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.mapper.NewProductMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.example.booktopia.model.*;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.service.ProductService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.example.booktopia.utils.RequestAttributeUtil.CATEGORIES;
import static org.example.booktopia.utils.RequestAttributeUtil.USER;
import static org.example.booktopia.utils.RequestParameterUtil.*;

import static org.example.booktopia.utils.RequestAttributeUtil.PAGE_TITLE;
import static org.example.booktopia.utils.RequestAttributeUtil.PRODUCT;

@Controller
@RequiredArgsConstructor
public class RequestBuilderUtil {
    private final CategoryController categoryController;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final NewProductMapper newProductMapper;
    private final ServletContext servletContext;
    private final ImageUtility imageUtility;
    private final PasswordEncoder passwordEncoder;

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

    private Set<Category> getInterests(HttpServletRequest request) {
        String[] categoryIds = request.getParameterValues(CATEGORIES);
        Set<Category> categories = new HashSet<>();
        for (String categoryId : categoryIds) {
            categories.add(categoryMapper.toEntity(categoryService.findById(Long.parseLong(categoryId))));
        }
        return categories;
    }

    private Account buildAccountFromRequest(HttpServletRequest request) {
        Account account = new Account();
        Address address = new Address();

        account.setName(request.getParameter(NAME));
        account.setBirthday(LocalDate.parse(request.getParameter(BIRTHDAY)));
        account.setJob(request.getParameter(JOB));
        account.setEmail(request.getParameter(EMAIL));
        account.setPassword(passwordEncoder.encode(request.getParameter(PASSWORD)));
        account.setPhoneNumber(request.getParameter(PHONE_NUMBER));

        address.setCountry(request.getParameter(COUNTRY));
        address.setCity(request.getParameter(CITY));
        address.setStreet(request.getParameter(STREET));
        address.setZipcode(request.getParameter(ZIPCODE));

        account.setAddress(address);
        return account;
    }
    public Admin updateAdminFromRequest(Admin admin,HttpServletRequest request) {
        Admin currentAdmin = admin;
        Account account = buildAccountFromRequest(request);
        admin.setAccount(account);
        return admin;
    }
    public Buyer updateBuyerFromRequest(Buyer buyer,HttpServletRequest request) {

        Buyer currentBuyer = buyer;
        Account account = buildAccountFromRequest(request);
        currentBuyer.setAccount(account);
        currentBuyer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(request.getParameter(CREDIT_LIMIT))));
        currentBuyer.setInterests(getInterests(request));
        return currentBuyer;
    }
    public Buyer createBuyerFromRequest(HttpServletRequest request) {
        Buyer buyer = new Buyer();
        Account account = buildAccountFromRequest(request);
        buyer.setAccount(account);
        buyer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(request.getParameter(CREDIT_LIMIT))));
        buyer.setInterests(getInterests(request));
        return buyer;
    }
}
