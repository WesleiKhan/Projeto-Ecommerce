package com.example.Ecommerce.utils.service;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;


@Service
public class StripePaymentServices {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public StripePaymentServices() {

        Stripe.apiKey = stripeApiKey;
    }

    public PaymentIntent createPaymentIntent(String token, long amount) throws StripeException {

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(amount)
            .setCurrency("brl")
            .setPaymentMethod(token) 
            .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC)
            .setConfirm(true)
            .setReturnUrl("https://sucesso.com/") // URL de redirecionamento
            .build();

    return PaymentIntent.create(params);
        } catch (StripeException e) {

            System.out.println("O Erro e " + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
