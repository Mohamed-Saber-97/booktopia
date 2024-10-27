package org.example.booktopia.viewcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.NewProductDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.NewProductMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Product;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.service.ProductService;
import org.example.booktopia.utils.RequestBuilderUtil;
import org.example.booktopia.utils.ValidatorUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller
@AllArgsConstructor
public class ProductsViewController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ValidatorUtil validatorUtil;
    private final RequestBuilderUtil requestBuilderUtil;
    private final ProductMapper productMapper;
    private final NewProductMapper newProductMapper;

    @GetMapping("/products")
    public String products(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> minPrice,
            @RequestParam Optional<String> maxPrice,
            Model model
    ) {
        List<Optional<String>> params = List.of(name, category, minPrice, maxPrice);
        List<ProductDto> productDtos = productService.search(params, 0, 16);
        List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
        model.addAttribute(PAGE_TITLE, "Products");
        model.addAttribute(PRODUCTS, productDtos);
        model.addAttribute(CATEGORIES, categoryDtos);
        return "products";
    }
}
