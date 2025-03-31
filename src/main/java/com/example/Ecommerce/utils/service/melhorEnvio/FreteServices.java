package com.example.Ecommerce.utils.service.melhorEnvio;

import com.example.Ecommerce.utils.service.melhorEnvio.interfaces.FreteAdpted;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.utils.exceptions.FreteException;
import com.example.Ecommerce.utils.service.DTOs.CepEntryDTO;
import com.example.Ecommerce.utils.service.DTOs.FreteEntryDTO;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class FreteServices implements FreteAdpted {
    
    private static final String API_URL = "https://sandbox.melhorenvio.com.br/api/v2/me/shipment/calculate";

    private static final String TOKEN_API = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5NTYiLCJqdGkiOiI0NDhjYmM1MDIzZTgxYmI2YjEwMmJlZmNjODQ3ZmQxYjRjZDk2NjUxZWU2MGMwOTRjNWU3ZmE0MTUyODMxMDE1ZjFiNTk4Zjk3ZGNhNDJmYyIsImlhdCI6MTczNzMwODkyNS41MjQ0NTEsIm5iZiI6MTczNzMwODkyNS41MjQ0NTUsImV4cCI6MTc2ODg0NDkyNS41MTIzMDcsInN1YiI6IjlkYmYxMDM2LTA4NzgtNGY5Yi05ODI3LTlmNzA5YzcwMzE1YiIsInNjb3BlcyI6WyJzaGlwcGluZy1jYWxjdWxhdGUiXX0.Kig39wT1mO7KazEuJ8eig7KPs4iW2qfnsBT7lsTDvJ5-LMFkrS_GAlG0PHfmed63YZyDXBKnKkL9PPVxp5GYTIVOSlPhwDWSsOlseq31jpnj--9JZS0i2bTYO7QZUBKyYTZZhXyR4-0iGZ7sNy2HNQoNTCfmrExoWK0yha6fbrDcvH5of61MCo-tJ5Lvbc-8iMfV5zPDpkXHpmgf5sb1CIIgmVKwcbtiZVMOaftdEiWuNW_R7-HKXy3t4xI_gn7IsbI45pezytpI8p-qGRkYJu0tSzPgdL_PAu5EkXUInI10ylF_MRNaEAQ-ZdxWpJwT9PUIyRxXBsWW8dhT2d7vKggqFpIbQ8h--Ap5PqSXK6W9w95hel0K6uyvJGvQ_1rsqjUYracHcNsPohg07RQhUptu7u2D5-_vpUQTxDmEaSRj8ArMPaXK4NhiAKzqBa1r1_QHGQeTS6fz8OfLFFkkaxTxTjMkzksbPMpSTzGTJMljuHfZQ60WxciFzLmJzltpKjQj-XFG5mTTSIMwhfOESsInn_HjItK8zb52ntxjJtfFgH8QDNVO-sUCEuRcxcHneo3tvj1gj_NQk6nG_Ys9Pe-vQz5NgMGk_C1qBR5sx5anOgN1uB48epFzuU1xCyysIdaBPIso3CtMqG5hU0BIUf2zlcT7YZZuodpOKvItdi4";

    private static final String USER_AGENT = "Aplicacao (Wesleikhan@gmailcom)";

    private final OkHttpClient client = new OkHttpClient();


    public String calcularFrete(FreteEntryDTO data, CepEntryDTO cep_destino) {

        try {

            String cepDestino = cep_destino.getCep();
            String cepOrigem = data.getCep_origem();
            int altura = (int) data.getAltura();      // altura como int
            int largura = (int) data.getLargura();    // largura como int
            int comprimento = (int) data.getComprimento();// comprimento como int
            double peso = data.getPeso();   // peso como float (o 'f' indica float)

            // Criar o JSON com a formatação desejada
            String json = String.format(
            "{\"from\":{\"postal_code\":\"%s\"}," +
                    "\"to\":{\"postal_code\":\"%s\"}," +
                    "\"package\":{\"height\":%d,\"width\":%d,\"length\":%d," +
                    "\"weight\":%s}}",
            cepOrigem, cepDestino, altura, largura, comprimento, peso
            );

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", TOKEN_API)
                .addHeader("User-Agent", USER_AGENT)
                .build();

                Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                
                // Extrair o tempo de entrega e o valor do frete diretamente
                String tempoEntrega = extractValue(responseBody, "\"delivery_time\":");
                String valorFrete = extractValue(responseBody, "\"price\":");

                return "Tempo de Entrega: " + tempoEntrega + " dias,\nValor do Frete: R$ " + valorFrete;
            } else {
                throw new RuntimeException("Erro ao calcular frete: " + response.message() + " - Código de resposta: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FreteException();
        }
    }

    private String extractValue(String responseBody, String key) {
        int startIdx = responseBody.indexOf(key);
        if (startIdx == -1) return "Não encontrado";
        
        int valueStart = responseBody.indexOf(":", startIdx) + 1;
        int valueEnd = responseBody.indexOf(",", valueStart);
        if (valueEnd == -1) valueEnd = responseBody.indexOf("}", valueStart);
        
        return responseBody.substring(valueStart, valueEnd).replace("\"", "").trim();
    }
}