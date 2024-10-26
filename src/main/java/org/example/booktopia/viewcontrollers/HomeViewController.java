package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.model.Country;
import org.example.booktopia.service.BuyerProductService;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller
@AllArgsConstructor
public class HomeViewController {
    private final ProductService productService;
    private final BuyerProductService buyerProductService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute(PAGE_TITLE, "Home");
        session.setAttribute(COUNTRIES, Country.countrySet);
        session.setAttribute(CATEGORIES, categoryService.findAllAvailableCategories());
        List<ProductDto> interests;
        if (session != null && session.getAttribute(BUYER) != null) {
            interests = buyerProductService.getBuyerInterestedProducts(((BuyerDto) session.getAttribute(USER)).id());
        } else {
            interests = productService.findFirst(16);
        }
        if (interests.size() < 16) {
            interests.addAll(productService.findFirst(16 - interests.size()));
        }
        model.addAttribute(INTERESTS, interests);
        return "index";
    }
}
