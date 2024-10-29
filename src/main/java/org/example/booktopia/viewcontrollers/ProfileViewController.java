package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.AdminDto;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.model.Country;
import org.example.booktopia.service.AdminService;
import org.example.booktopia.service.BuyerInterestService;
import org.example.booktopia.service.BuyerService;
import org.example.booktopia.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller
@AllArgsConstructor
public class ProfileViewController {

    private final AdminService adminService;
    private final BuyerService buyerService;
    private final CategoryService categoryService;
    private final BuyerInterestService buyerInterestService;
    private final UpdateUserSession updateUserSession;

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        updateUserSession.updateUserSession(request);
        model.addAttribute("pageTitle", "My Profile");
        model.addAttribute(CATEGORIES, categoryService.findAllAvailableCategories());
        model.addAttribute(COUNTRIES, Country.countrySet);
        if (session.getAttribute(BUYER) != null) {
            Long buyerId = ((BuyerDto) session.getAttribute(USER)).id();
            List<Long> categoryIds = buyerInterestService.findCategoryIdsByBuyerId(buyerId);
            model.addAttribute("interests", categoryIds);
        }
        return "profile";
    }

    @PostMapping("/update-profile")
    public String profileSubmit(
            HttpServletRequest request,
            Model model,
            HttpSession session) {
        model.addAttribute(PAGE_TITLE, "Profile");
        var isBuyer = session.getAttribute(BUYER) != null;
        var isAdmin = session.getAttribute(ADMIN) != null;
        if (isAdmin) {
            AdminDto currentAdmin = adminService.updateProfile(request);
            session.setAttribute(USER, currentAdmin);
            session.setAttribute(ADMIN, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            return "redirect:/profile";
        }
        if (isBuyer) {
            BuyerDto currentBuyer = buyerService.updateProfile(request);
            session.setAttribute(USER, currentBuyer);
            session.setAttribute(BUYER, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

}
