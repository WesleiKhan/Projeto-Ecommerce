package com.example.Ecommerce.utils.service.stripe.interfaces;

import com.stripe.exception.StripeException;

public interface StripePayment {

    String createPaymentIntent(String token, long amount) throws StripeException;
}
