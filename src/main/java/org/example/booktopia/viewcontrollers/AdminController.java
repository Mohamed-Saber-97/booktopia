package org.example.booktopia.viewcontrollers;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.example.booktopia.utils.RequestAttributeUtil.ERROR;
import static org.example.booktopia.utils.RequestAttributeUtil.PAGE_TITLE;

@Controller("AdminController")
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

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

}
