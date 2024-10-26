package org.example.booktopia.utils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.AdminDto;
import org.example.booktopia.model.Admin;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.service.AdminService;
import org.example.booktopia.service.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.booktopia.utils.RequestAttributeUtil.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validator")
public class Validator {
    private final BuyerService buyerService;
    private final AdminService adminService;

    @GetMapping("/check-unique-email")
    public String checkUniqueEmail(String email) {
        return buyerService.existsByAccountEmail(email) ? "Email already exists" : "true";
    }

    @GetMapping("/check-unique-phone-number")
    public String checkUniquePhoneNumber(String phoneNumber) {
        return buyerService.existsByAccountPhone(phoneNumber) ? "Phone number already exists" : "true";
    }

    @PostMapping("/admin-update-email")
    public ResponseEntity<String> validateEmail(@RequestParam("email") String email, HttpSession session) {
        AdminDto currentAdmin = (AdminDto) session.getAttribute(USER);
        if (currentAdmin == null) {
            return ResponseEntity.badRequest()
                                 .body("User not found.");
        }
        AdminDto existingAdmin = null;
        try {
            existingAdmin = adminService.findByEmail(email);
        } catch (Exception e) {
            return ResponseEntity.ok("true"); // Email is available
        }
        if (existingAdmin != null && !existingAdmin.id()
                                                   .equals(currentAdmin.id())) {
            return ResponseEntity.ok("Email is already taken by someone else.");
        }
        return ResponseEntity.ok("true"); // Email is available
    }

    @PostMapping("/buyer-update-email")
    public ResponseEntity<String> validateBuyerEmail(@RequestParam("email") String email, HttpSession session) {
        Buyer currentBuyer = (Buyer) session.getAttribute(USER);
        if (currentBuyer == null) {
            return ResponseEntity.badRequest()
                                 .body("User not found.");
        }
        Buyer existingBuyer = null;
        try {
            existingBuyer = buyerService.findBuyerByEmail(email);
        } catch (Exception e) {
            return ResponseEntity.ok("true"); // Email is available
        }
        if (existingBuyer != null && !existingBuyer.getId()
                                                   .equals(currentBuyer.getId())) {
            return ResponseEntity.ok("Email is already taken by someone else.");
        }
        return ResponseEntity.ok("true"); // Email is available
    }


    @PostMapping("/admin-update-phone-number")
    public ResponseEntity<String> validatePhonenumber(@RequestParam("phoneNumber") String phoneNumber,
                                                      HttpSession session) {
        AdminDto currentAdmin = (AdminDto) session.getAttribute(USER);
        if (currentAdmin == null) {
            return ResponseEntity.badRequest()
                                 .body("User not found.");
        }
        Admin existingAdmin = null;
        try {
            existingAdmin = adminService.findByPhonenumber(phoneNumber);
        } catch (Exception e) {
            return ResponseEntity.ok("true"); // Phonenumber is available
        }
        if (existingAdmin != null && !existingAdmin.getId()
                                                   .equals(currentAdmin.id())) {
            return ResponseEntity.ok("Phone number is already taken by someone else.");
        }
        return ResponseEntity.ok("true"); // Email is available
    }

    @PostMapping("/buyer-update-phone-number")
    public ResponseEntity<String> validateBuyerPhonenumber(@RequestParam("phoneNumber") String phoneNumber,
                                                           HttpSession session) {
        Buyer currentBuyer = (Buyer) session.getAttribute(USER);
        if (currentBuyer == null) {
            return ResponseEntity.badRequest()
                                 .body("User not found.");
        }
        Buyer existingBuyer = null;
        try {
            existingBuyer = buyerService.findBuyerByPhonenumber(phoneNumber);
        } catch (Exception e) {
            return ResponseEntity.ok("true"); // Phonenumber is available
        }
        if (existingBuyer != null && !existingBuyer.getId()
                                                   .equals(currentBuyer.getId())) {
            return ResponseEntity.ok("Phone number is already taken by someone else.");
        }
        return ResponseEntity.ok("true"); // Email is available
    }
}
