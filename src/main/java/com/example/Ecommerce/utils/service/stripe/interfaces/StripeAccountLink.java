package com.example.Ecommerce.utils.service.stripe.interfaces;

import com.stripe.exception.StripeException;

public interface StripeAccountLink {

    String criarLinkDeOnboading(String id_account) throws StripeException;
}
