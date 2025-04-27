package com.example.Ecommerce.client.service.sendGrid.contract;

import java.io.IOException;

public interface EmailSender {

    void sendEmail(String contentEmail, String emailDestinatario) throws IOException;
}
