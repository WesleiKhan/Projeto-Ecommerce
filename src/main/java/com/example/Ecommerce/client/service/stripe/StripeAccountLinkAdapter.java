package com.example.Ecommerce.client.service.stripe;

import java.util.HashMap;
import java.util.Map;

import com.example.Ecommerce.client.service.stripe.interfaces.StripeAccountLink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.AccountLink;

@Service
public class StripeAccountLinkAdapter implements StripeAccountLink {

    @Value("${stripe.api.key}")
    private String apiKey;

    public String criarLinkDeOnboading(String id_account) throws StripeException {

        Stripe.apiKey = apiKey;

        try {

            Map<String, Object> params = new HashMap<>();

            params.put("account", id_account);
            params.put("refresh_url", "https://ecommerce/reatenticacao");
            params.put("return_url", "https://sucesso.com/");
            params.put("type", "account_onboarding");

            AccountLink accountLink = AccountLink.create(params);

            return accountLink.getUrl();
            
        } catch (StripeException e) {
            
            System.out.println("O Erro e " + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
