package com.example.Ecommerce.user.exceptions;

public class UserNotFound extends RuntimeException {

    public UserNotFound() {super("Usuario não foi encontrado!");}

    public UserNotFound(String message) {super(message);}
    
}
