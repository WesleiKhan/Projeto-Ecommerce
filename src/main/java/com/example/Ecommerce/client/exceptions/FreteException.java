package com.example.Ecommerce.client.exceptions;

public class FreteException extends RuntimeException {

    public FreteException() {super("Error ao Efetuar o calculo de frete");}

    public FreteException(String message) {super(message);}
    
}
