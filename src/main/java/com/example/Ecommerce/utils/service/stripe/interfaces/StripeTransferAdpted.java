package com.example.Ecommerce.utils.service.stripe.interfaces;

import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;

public interface StripeTransferAdpted {

    Transfer createTransfer(String account_id, String id_charge, long amount)
            throws StripeException;
}
