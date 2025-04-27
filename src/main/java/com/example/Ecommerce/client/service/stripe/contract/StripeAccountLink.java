package com.example.Ecommerce.client.service.stripe.contract;

import com.stripe.exception.StripeException;

public interface StripeAccountLink {

    String criarLinkDeOnboading(String id_account) throws StripeException;
}
