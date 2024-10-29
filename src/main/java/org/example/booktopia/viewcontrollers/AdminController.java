package org.example.booktopia.viewcontrollers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.*;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.model.Order;
import org.example.booktopia.service.*;
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
    private final BuyerService buyerService;
    private final BuyerMapper buyerMapper;
    private final BuyerInterestService buyerInterestService;
    private final BuyerProductService buyerProductService;
    private final OrderService orderService;

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

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute CategoryDto categoryDto, Model model) {
        try {
            categoryService.save(categoryDto);
            model.addAttribute(PAGE_TITLE, "Categories");
            return "redirect:/admins/categories";
        } catch (Exception e) {
            model.addAttribute(ERROR, "Error while adding category");
            model.addAttribute(PAGE_TITLE, "Add Category");
            return "add-category";
        }
    }

    @GetMapping("/edit-category")
    public String editCategory(@RequestParam Long p, Model model) {
        try {
            CategoryDto categoryDto = categoryService.findById(p);
            model.addAttribute(PAGE_TITLE, "Edit Category");
            model.addAttribute(CATEGORY, categoryDto);
            return "edit-category";
        } catch (RecordNotFoundException e) {
            model.addAttribute(ERROR, "Category not found");
            model.addAttribute(PAGE_TITLE, "Categories");
            return "categories";
        }
    }

    @PostMapping("/edit-category")
    public String editCategory(@ModelAttribute CategoryDto categoryDto, Model model) {
        try {
            categoryService.update(categoryDto.id(), categoryDto);
            model.addAttribute(PAGE_TITLE, "Categories");
            return "redirect:/admins/categories";
        } catch (Exception e) {
            model.addAttribute(ERROR, "Error while updating category");
            model.addAttribute(PAGE_TITLE, "Edit Category");
            return "edit-category";
        }
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

    @GetMapping("/categories")
    public String categories(Model model) {
        List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
        model.addAttribute(PAGE_TITLE, "Categories");
        model.addAttribute(CATEGORIES, categoryDtos);
        return "categories";
    }

    @PostMapping("/delete-category")
    public String deleteCategory(Long id, Model model) {
        categoryService.deleteById(id);
        model.addAttribute(PAGE_TITLE, "Categories");
        return "redirect:/admins/categories";
    }

    @GetMapping("/buyers")
    public String buyers(Model model) {
        List<BuyerDto> buyerDtos = buyerService.getAllBuyers(0, 16);
        model.addAttribute(PAGE_TITLE, "Buyers");
        model.addAttribute(BUYERS, buyerDtos);
        return "buyers";
    }

    @GetMapping("/buyer-profile")
    public String buyerProfile(@RequestParam Long p, Model model, HttpSession session) {
        try {
            Buyer buyer = buyerService.findById(p);
            List<CategoryDto> categoryDtos = categoryService.findAllAvailableCategories();
            BuyerDto buyerDto = buyerMapper.toDto(buyer);
            List<Long> categoryIds = buyerInterestService.findCategoryIdsByBuyerId(buyer.getId());
            model.addAttribute(PAGE_TITLE, "Buyer Profile");
            model.addAttribute(TEMP_BUYER, buyerDto);
            model.addAttribute(CATEGORIES, categoryDtos);
            model.addAttribute(CATEGORY_IDS, categoryIds);
        } catch (RecordNotFoundException e) {
            model.addAttribute(ERROR, "Buyer not found");
            model.addAttribute(PAGE_TITLE, "Buyers");
            return "buyers";
        }
        return "buyer-profile";
    }

    @GetMapping("/buyer-orders")
    public String buyerOrders(@RequestParam Long p, Model model) {
        try {
            Buyer buyer = buyerService.findById(p);
            BuyerDto buyerDto = buyerMapper.toDto(buyer);
            List<OrderDto> orderDtos = buyerService.getOrdersByBuyerId(p);
            model.addAttribute(PAGE_TITLE, "Buyer Orders");
            model.addAttribute(TEMP_BUYER, buyerDto);
            model.addAttribute(ORDERS, orderDtos);
            return "buyer-orders";
        } catch (RecordNotFoundException e) {
            model.addAttribute(ERROR, "Buyer not found");
            model.addAttribute(PAGE_TITLE, "Buyers");
            return "buyers";
        }
    }

    @GetMapping("/buyer-order-products")
    public String buyerOrders(@RequestParam Long p, @RequestParam Long order, Model model) {
        try {
            Buyer buyer = buyerService.findById(p);
            if (buyer.getOrders().stream().map(Order::getId).noneMatch(id -> id.equals(order))) {
                model.addAttribute(ERROR, "Order not found");
                model.addAttribute(PAGE_TITLE, "Buyer Orders");
                return "buyer-orders";
            }
            BuyerDto buyerDto = buyerMapper.toDto(buyer);
            List<OrderProductDto> orderProductDtos = orderService.findAllProductByOrderId(order);
            model.addAttribute(PAGE_TITLE, "Buyer Orders");
            model.addAttribute(TEMP_BUYER, buyerDto);
            model.addAttribute(PRODUCTS, orderProductDtos);
            return "buyer-order-products";

        } catch (Exception e) {
            model.addAttribute(ERROR, "Error while fetching order products");
            model.addAttribute(PAGE_TITLE, "Buyer Orders");
            return "buyer-orders";
        }
    }
}
