package com.example.Ecommerce.utils.service;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import com.stripe.param.TransferCreateParams;

@Service
public class StripeTransferServices {

    //@Value("${stripe.api.key}")
    //private String apiKey;

    public Transfer createTransfer(String account_id, String id_charge, long amount) throws StripeException {

        Stripe.apiKey = "sk_test_51QFcGMGVRGySusxwg1Og8ykr499zS4HFNOz4YCQWZXMPHgQtVKvxOdDoxqZ0HnNITnh5CaBFU8l5HvlmjK70N4bC009dcq0yFW";

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
