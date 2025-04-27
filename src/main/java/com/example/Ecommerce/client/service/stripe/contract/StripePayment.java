package com.example.Ecommerce.client.service.stripe.contract;

import com.stripe.exception.StripeException;

public interface StripePayment {

    String createPaymentIntent(String token, long amount) throws StripeException;
}
