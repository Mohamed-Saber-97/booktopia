package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.service.BuyerProductService;
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

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute(PAGE_TITLE, "Home");
        System.out.println(session.getAttribute(BUYER));
        List<ProductDto> interests;
        if (session.getAttribute(BUYER) != null) {
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
