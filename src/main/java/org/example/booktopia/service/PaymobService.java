package org.example.booktopia.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;

@Service
public class PaymobService {
    @Value("${paymob.api.key}")
    private String apiKey;
    @Value("${paymob.api.url}")
    private String apiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public String authenticate() {
        String url = apiUrl + "/auth/tokens";
        Map<String, String> request = new HashMap<>();
        request.put("api_key", apiKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        return response.getBody().get("token").toString();
    }

    public String registerOrder(String token, int amountCents) {
        String url = apiUrl + "/ecommerce/orders";
        Map<String, Object> request = new HashMap<>();
        request.put("merchant_id", 1002783);
        request.put("amount_cents", amountCents);
        request.put("currency", "EGP");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        return response.getBody().get("id").toString();
    }

    public String generatePaymentKey(String token, String orderId, int amountCents) {
        String url = apiUrl + "/acceptance/payment_keys";
        Map<String, Object> request = new HashMap<>();
        request.put("amount_cents", amountCents);
        request.put("expiration", 3600);
        request.put("order_id", orderId);
        request.put("currency", "EGP");
        request.put("integration_id", "4862856");//4862304
        Map<String, String> billingData = new HashMap<>();
        billingData.put("email", "customer@example.com");
        billingData.put("first_name", "John");
        billingData.put("last_name", "Doe");
        billingData.put("phone_number", "+201234567890");
        billingData.put("city", "Cairo");
        billingData.put("building", "12");
        billingData.put("floor", "2");
        billingData.put("apartment", "8");
        billingData.put("country", "EGY");
        billingData.put("street", "12 street");
        request.put("billing_data", billingData);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        return response.getBody().get("token").toString();
    }
}