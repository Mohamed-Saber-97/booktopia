package org.example.booktopia.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.NewProductDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.mapper.NewProductMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Product;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.validator.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.example.booktopia.utils.RequestAttributeUtil.ERROR;
import static org.example.booktopia.utils.RequestAttributeUtil.PRODUCT;

@Controller
@AllArgsConstructor
public class ValidatorUtil {
    private final CategoryValidator categoryValidator;
    private final UniqueIsbnValidator uniqueIsbnValidator;
    private final NewProductMapper newProductMapper;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    public Map<String, String> commonBookValidation(HttpServletRequest request, Product product) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        BigDecimal price = product.getPrice();
        Integer quantity = product.getQuantity();
        LocalDate releaseDate = product.getReleaseDate();
        Long categoryId = product.getCategory().getId();
        String isbn = product.getIsbn();
        String author = product.getAuthor();
        String name = product.getName();
        String description = product.getDescription();
        Part image = request.getPart("image");
        System.out.println("My image path: " + image);
        if (!NotEmptyValidator.isValid(isbn, author, name, description)) {
            errors.put(ERROR, NotEmptyValidator.ERROR_MESSAGE);
        } else if (!categoryValidator.isValid(categoryId)) {
            errors.put(ERROR, CategoryValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, name)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(100, author)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(13, isbn)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!MaxFieldLengthValidator.isValid(500, description)) {
            errors.put(ERROR, MaxFieldLengthValidator.ERROR_MESSAGE);
        } else if (!ObjectNotNullValidator.isValid(price, quantity, releaseDate, categoryId)) {
            errors.put(ERROR, ObjectNotNullValidator.ERROR_MESSAGE);
        } else if (!(image.getSize() > 0)) {
            errors.put(ERROR, "Image is required");
        }
        return errors;
    }

    public Map<String, String> validateAddBook(HttpServletRequest request, NewProductDto productDto) throws ServletException, IOException {
        Product product = newProductMapper.toEntity(productDto);
        Map<String, String> errors = new HashMap<>(commonBookValidation(request, product));
        String isbn = product.getIsbn();

        if (!uniqueIsbnValidator.isValid(isbn)) {
            errors.put(ERROR, UniqueIsbnValidator.ERROR_MESSAGE);
        }
        return errors;
    }

    public Map<String, String> validateUpdateBook(HttpServletRequest request, ProductDto productDto) throws ServletException, IOException {
        Product product = productMapper.toEntity(productDto);
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));
        System.out.println("My category id: " + categoryId);
        CategoryDto categoryDto = categoryService.findById(categoryId);
        product.setCategory(categoryMapper.toEntity(categoryDto));
        Map<String, String> errors = new HashMap<>(commonBookValidation(request, product));
        String oldIsbn = ((ProductDto) request.getSession().getAttribute(PRODUCT)).isbn();
        String newIsbn = productDto.isbn();
//                request.getParameter(ISBN);

        if (!oldIsbn.equals(newIsbn) && !uniqueIsbnValidator.isValid(newIsbn)) {
            errors.put(ERROR, UniqueIsbnValidator.ERROR_MESSAGE);
        }
        return errors;
    }
}
