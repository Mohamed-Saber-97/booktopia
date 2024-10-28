package org.example.booktopia.payment;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PaymentIntentController {
    @PostMapping("/create-payment-intent")
    public Response createPaymentIntent(@RequestBody Request request)
            throws StripeException {
        BigDecimal paymentAmount = request.getAmount();
        BigDecimal paymentMultiplicator = new BigDecimal(100);
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(paymentAmount.multiply(paymentMultiplicator).longValue())
                        .putMetadata("productName",
                                request.getProductName())
                        .setCurrency("usd")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams
                                        .AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent intent =
                PaymentIntent.create(params);

        return new Response(intent.getId(),
                intent.getClientSecret());
    }
}
