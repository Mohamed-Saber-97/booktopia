package org.example.booktopia.viewcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.example.booktopia.utils.RequestAttributeUtil.PAGE_TITLE;

@Controller
public class JspPageProvider {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(PAGE_TITLE, "Home");
        return "index";
    }
}
