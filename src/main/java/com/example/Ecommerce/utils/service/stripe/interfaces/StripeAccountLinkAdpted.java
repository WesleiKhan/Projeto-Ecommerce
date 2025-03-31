package com.example.Ecommerce.utils.service.stripe.interfaces;

import com.stripe.exception.StripeException;

public interface StripeAccountLinkAdpted {

    String criarLinkDeOnboading(String id_account) throws StripeException;
}
