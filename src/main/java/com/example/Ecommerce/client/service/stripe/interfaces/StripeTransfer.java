package com.example.Ecommerce.client.service.stripe.interfaces;

import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;

public interface StripeTransfer {

    Transfer createTransfer(String account_id, String id_charge, long amount)
            throws StripeException;
}
