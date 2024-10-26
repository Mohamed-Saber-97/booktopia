package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.example.booktopia.utils.RequestAttributeUtil.PAGE_TITLE;

@Controller
@AllArgsConstructor
public class LogoutViewController {

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute(PAGE_TITLE, "Home");
        return "redirect:/";
    }
}
