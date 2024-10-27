package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller
@AllArgsConstructor
public class ProductsViewController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UpdateUserSession updateUserSession;

    @GetMapping("/products")
    public String products(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> minPrice,
            @RequestParam Optional<String> maxPrice,
            Model model, HttpServletRequest request
    ) {
        updateUserSession.updateUserSession(request);
        List<Optional<String>> params = List.of(name, category, minPrice, maxPrice);
        List<ProductDto> productDtos = productService.search(params, 0, 16);
        List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
        model.addAttribute(PAGE_TITLE, "Products");
        model.addAttribute(PRODUCTS, productDtos);
        model.addAttribute(CATEGORIES, categoryDtos);
        return "products";
    }

    @GetMapping("/product")
    public String viewProduct(@RequestParam Long p, Model model, HttpServletRequest request) {
        try {
            updateUserSession.updateUserSession(request);
            ProductDto productDto = productService.findProductById(p);
            model.addAttribute(PAGE_TITLE, productDto.name());
            model.addAttribute(PRODUCT, productDto);
            return "product-details";
        } catch (Exception e) {
            return "redirect:/products";
        }
    }
}
