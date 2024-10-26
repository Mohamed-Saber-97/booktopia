package org.example.booktopia.controller;

import org.example.booktopia.service.PaymobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymob")
public class PaymobController {

    private final PaymobService paymobService;

    public PaymobController(PaymobService paymobService) {
        this.paymobService = paymobService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestParam int amountCents) {
        String token = paymobService.authenticate();
        String orderId = paymobService.registerOrder(token, amountCents);
        String paymentKey = paymobService.generatePaymentKey(token, orderId, amountCents);
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

