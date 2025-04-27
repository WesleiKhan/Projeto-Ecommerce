package com.example.Ecommerce.client.service.stripe.contract;

import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;

public interface StripeTransfer {

    Transfer createTransfer(String account_id, String id_charge, long amount)
            throws StripeException;
}
