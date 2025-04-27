package com.example.Ecommerce.client.service.sendGrid;

import java.io.IOException;

import com.example.Ecommerce.client.service.sendGrid.contract.EmailSender;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GridAdapterSender implements EmailSender {


    @Value("${sendgrid.email.remetente}")
    private String remetente;

    @Value("${sendgrid.api.key}")
    private String apiKey;

    public void sendEmail(String contentEmail, String emailDestinatario) throws IOException {

        Email from = new Email(remetente);
        String subject = "E-mail para o vendedor terminar cadastro";
        Email to = new Email(emailDestinatario);
        Content content = new Content("text/html", "Clique no link para " +
                "continuar o cadastro: <a href=\"" + contentEmail + "\">Confirmar</a>");
        Mail mail = new Mail(from, subject, to, content);

        // Cria a instância do cliente do SendGrid usando a sua API Key
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());

        } catch (IOException e) {
            System.out.println("O Erro e " + e);
            e.printStackTrace();
            throw e;
        }


    }
    
}
