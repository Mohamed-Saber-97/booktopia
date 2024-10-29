package org.example.booktopia.controller;

import lombok.AllArgsConstructor;
import org.example.booktopia.service.PaymobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymob")
@AllArgsConstructor
public class PaymobController {

    private final PaymobService paymobService;

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestParam int amountCents) {
        String token = paymobService.authenticate();
        System.out.println("Token: " + token);
        String orderId = paymobService.registerOrder(token, amountCents);
        System.out.println("After registerOrder: " + orderId);
        String paymentKey = paymobService.generatePaymentKey(token, orderId, amountCents);
        System.out.println("After generatePaymentKey: " + paymentKey);
        return ResponseEntity.ok(paymentKey);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload) {
        if (payload.containsKey("obj") && payload.get("type").equals("TRANSACTION")) {
            Map<String, Object> transaction = (Map<String, Object>) payload.get("obj");
            boolean success = (Boolean) transaction.get("success");
            if (success) {
                // Update order status to "Paid"
            } else {
                // Update order status to "Failed"
            }
        }
        return ResponseEntity.ok().build();
    }
}