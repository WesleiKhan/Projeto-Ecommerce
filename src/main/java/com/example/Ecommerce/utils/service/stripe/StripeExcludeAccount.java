package com.example.Ecommerce.utils.service.stripe;

import com.example.Ecommerce.utils.service.stripe.interfaces.StripeExcludeAccountAdpted;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class StripeExcludeAccount implements StripeExcludeAccountAdpted {

    @Value("${stripe.api.key}")
    private String apiStripeKey;

    public String deleteAccountStripe(String idAccountStripe) throws Exception {

        String url = "https://api.stripe.com/v1/accounts/" + idAccountStripe;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiStripeKey)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.statusCode() + ": " + response.body();
    }
}
