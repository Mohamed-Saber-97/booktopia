package org.example.booktopia.utils;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.BuyerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validator")
public class Validator {
    private final BuyerService buyerService;

    @GetMapping("/check-unique-email")
    public String checkUniqueEmail(String email) {
        return buyerService.existsByAccountEmail(email) ? "Email already exists" : "true";
    }

    @GetMapping("/check-unique-phone-number")
    public String checkUniquePhoneNumber(String phoneNumber) {
        return buyerService.existsByAccountPhone(phoneNumber) ? "Phone number already exists" : "true";
    }
}
