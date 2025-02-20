package com.example.Ecommerce.utils.service.sendGrid;

import java.io.IOException;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendGridServices {


    @Value("${sendgrid.email.remetente}")
    private String remetente;

    public void sendEmail(String contentEmail, String emailDestinatario) throws IOException {

        String apiKey = "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";

        Email from = new Email(remetente);
        String subject = "Enviando e-mail para o vendedor terminar cadastro";
        Email to = new Email(emailDestinatario);
        Content content = new Content("text/html", "Clique no link para continuar o cadastro: <a href=\"" + contentEmail + "\">Confirmar</a>");
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
