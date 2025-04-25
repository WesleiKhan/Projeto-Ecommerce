package com.example.Ecommerce.utils.service.stripe;

import com.example.Ecommerce.utils.service.stripe.interfaces.StripeTransfer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import com.stripe.param.TransferCreateParams;

@Service
public class StripeTransferAdapter implements StripeTransfer {

    @Value("${stripe.api.key}")
    private String apiKey;

    public Transfer createTransfer(String account_id, String id_charge, long amount)
            throws StripeException {

        Stripe.apiKey = apiKey;

        try {
            Transfer transfer = Transfer.create(
                new TransferCreateParams.Builder()
                        .setAmount(amount)  
                        .setCurrency("brl")                
                        .setDestination(account_id) 
                        .setSourceTransaction(id_charge)        
                        .build()
                );

            return transfer;    
            
        } catch (StripeException e) {
            
            System.out.println("O Erro e " + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
