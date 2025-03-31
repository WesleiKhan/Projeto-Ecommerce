package com.example.Ecommerce.utils.service.sendGrid.interfaces;

import java.io.IOException;

public interface SendGridAdpted {

    void sendEmail(String contentEmail, String emailDestinatario) throws IOException;
}
