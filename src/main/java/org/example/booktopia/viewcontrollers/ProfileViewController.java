package org.example.booktopia.viewcontrollers;

import lombok.AllArgsConstructor;
import org.example.booktopia.controller.CategoryController;
import org.example.booktopia.model.Country;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ProfileViewController {

    private final CategoryController categoryController;

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("pageTitle", "My Profile");
        model.addAttribute("countries", Country.countrySet);
        model.addAttribute("categories", categoryController.getAllCategories());
        return "profile";
    }

}
