package com.example.Ecommerce.user.exceptions;

public class UserNotAutorization extends RuntimeException{

    public UserNotAutorization() {super("Usuario não Autorizado.");}

    public UserNotAutorization(String message) {super(message);}
}
