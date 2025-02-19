package com.example.Ecommerce.utils.service;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;


@Service
public class StripePaymentServices {

    //@Value("${stripe.api.key}")
    //private String stripeApiKey;

    public StripePaymentServices() {

        Stripe.apiKey = "sk_test_51QFcGMGVRGySusxwg1Og8ykr499zS4HFNOz4YCQWZXMPHgQtVKvxOdDoxqZ0HnNITnh5CaBFU8l5HvlmjK70N4bC009dcq0yFW";
    }

    public String createPaymentIntent(String token, long amount) throws StripeException {

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(amount)
            .setCurrency("brl")
            .setPaymentMethod(token) 
            .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC)
            .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.AUTOMATIC)
            .setConfirm(true)
            .setReturnUrl("https://sucesso.com/") // URL de redirecionamento
            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            
            // Verifica se o pagamento foi bem-sucedido
            if ("succeeded".equals(intent.getStatus())) {
            // Obtém o ID da charge (ch_...)
            return intent.getLatestCharge();
            }

            return null;
            
        } catch (StripeException e) {

            System.out.println("O Erro e " + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
