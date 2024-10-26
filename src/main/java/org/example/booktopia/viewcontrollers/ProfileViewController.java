package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.booktopia.controller.CategoryController;
import org.example.booktopia.dtos.AdminDto;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.model.Country;
import org.example.booktopia.service.AdminService;
import org.example.booktopia.service.BuyerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller
@AllArgsConstructor
public class ProfileViewController {

    private final CategoryController categoryController;
    private final AdminService adminService;
    private final BuyerService buyerService;

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("pageTitle", "My Profile");
        model.addAttribute("countries", Country.countrySet);
        model.addAttribute("categories", categoryController.getAllCategories());
        return "profile";
    }

    @PostMapping("/update-profile")
    public String profileSubmit(@ModelAttribute AdminDto adminDto, @ModelAttribute BuyerDto buyerDto, Model model,
                                HttpSession session) {
        model.addAttribute(PAGE_TITLE, "Profile");
        var isBuyer = session.getAttribute(BUYER) != null;
        var isAdmin = session.getAttribute(ADMIN) != null;
        if (isAdmin) {
            AdminDto currentAdmin = (AdminDto) session.getAttribute(USER);
            currentAdmin = adminService.updateProfile(currentAdmin.id(), adminDto);
            session.setAttribute(USER, currentAdmin);
            session.setAttribute(ADMIN, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            return "redirect:/profile";
        }
        if (isBuyer) {
            BuyerDto currentBuyer = (BuyerDto) session.getAttribute(BUYER);
            session.setAttribute(USER, currentBuyer);
            session.setAttribute(ADMIN, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

}
