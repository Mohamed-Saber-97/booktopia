package org.example.booktopia.viewcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.NewProductDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.service.AdminService;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.service.ProductService;
import org.example.booktopia.utils.RequestBuilderUtil;
import org.example.booktopia.utils.ValidatorUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller("AdminController")
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final RequestBuilderUtil requestBuilderUtil;
    private final ValidatorUtil validatorUtil;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute(PAGE_TITLE, "Admin Login");
        return "admin-login";
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        model.addAttribute(PAGE_TITLE, "Add Category");
        return "add-category";
    }

    @GetMapping("/books")
    public String books(Model model) {
        List<ProductDto> productDtos = productService.search(List.of(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()), 0, 16);
        List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
        model.addAttribute(PAGE_TITLE, "Books");
        model.addAttribute(PRODUCTS, productDtos);
        model.addAttribute(CATEGORIES, categoryDtos);
        return "books";
    }

    @GetMapping("/add-book")
    public String addBook(Model model) {
        List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
        model.addAttribute(PAGE_TITLE, "Add a Book");
        model.addAttribute(CATEGORIES, categoryDtos);
        return "add-book";
    }

    @PostMapping("/add-book")
    @Transactional
    public String addBook(@ModelAttribute NewProductDto newProductDto, HttpServletRequest request, Model model) throws ServletException, IOException {
//        Product newProduct = newProductMapper.toEntity(newProductDto);
        Map<String, String> errors = validatorUtil.validateAddBook(request, newProductDto);
        if (!errors.isEmpty()) {
            model.addAttribute(ERROR, errors.get(ERROR));
            model.addAttribute(PAGE_TITLE, "Add a Book");
            return "add-book";
        } else {
            try {
                requestBuilderUtil.createProductFromRequest(request, newProductDto);
            } catch (Exception e) {
                model.addAttribute(ERROR, "Error while adding book");
                List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
                model.addAttribute(PAGE_TITLE, "Add a Book");
                model.addAttribute(CATEGORIES, categoryDtos);
                return "add-book";
            }
            model.addAttribute(PAGE_TITLE, "Home");
            return "redirect:/admins/books";
        }
    }

    @PostMapping("/delete-book")
    public String deleteBook(Long id, Model model) {
        productService.deleteById(id);
        model.addAttribute(PAGE_TITLE, "Books");
        return "redirect:/admins/books";
    }

    @GetMapping("/edit-book")
    public String editBook(@RequestParam Long p, Model model, HttpSession session) {
        try {
            ProductDto productDto = productService.findProductById(p);
            List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
            model.addAttribute(PAGE_TITLE, "Edit Book");
//            model.addAttribute(PRODUCT, productDto);
            session.setAttribute(PRODUCT, productDto);
            model.addAttribute(CATEGORIES, categoryDtos);
            return "edit-book";
        } catch (RecordNotFoundException e) {
            model.addAttribute(ERROR, "Book not found");
            model.addAttribute(PAGE_TITLE, "Books");
            return "books";
        }
    }

    @PostMapping("/edit-book")
    @Transactional
    public String editBook(@ModelAttribute ProductDto productDto, HttpServletRequest request, Model model) throws ServletException, IOException {
        Map<String, String> errors = validatorUtil.validateUpdateBook(request, productDto);
        if (!errors.isEmpty()) {
            model.addAttribute(ERROR, errors.get(ERROR));
            model.addAttribute(PAGE_TITLE, "Edit Book");
            List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
            model.addAttribute(CATEGORIES, categoryDtos);
            return "edit-book";
        } else {
            try {
                requestBuilderUtil.updateProductFromRequest(request, productDto);
            } catch (Exception e) {
                model.addAttribute(ERROR, "Error while updating book");
                model.addAttribute(PAGE_TITLE, "Edit Book");
                return "edit-book";
            }
            model.addAttribute(PAGE_TITLE, "Books");
            return "redirect:/admins/books";
        }
    }
}
