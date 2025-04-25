package com.example.Ecommerce.client.service.sendGrid.interfaces;

import java.io.IOException;

public interface EmailSender {

    void sendEmail(String contentEmail, String emailDestinatario) throws IOException;
}
