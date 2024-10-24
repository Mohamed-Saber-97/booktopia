package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.LoginDto;
import org.example.booktopia.error.InvalidLoginCredentialsException;
import org.example.booktopia.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
//    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
    public String login(@ModelAttribute LoginDto loginDto, Model model, HttpSession session) {

        model.addAttribute("PAGE_TITLE", "Admin Dashboard");
        try {
            adminService.login(loginDto);
            return "redirect:/";
        } catch (InvalidLoginCredentialsException e) {
            model.addAttribute(ERROR, "Invalid login credentials. Please try again.");
            return "admin-login";
        }
    }
}
