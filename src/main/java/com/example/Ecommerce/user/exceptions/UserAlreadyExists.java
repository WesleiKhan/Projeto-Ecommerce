package com.example.Ecommerce.user.exceptions;

public class UserAlreadyExists extends RuntimeException {

    public UserAlreadyExists() {super("Usuario ja e cadastrado!");}

    public UserAlreadyExists(String message) {super(message);}
    
}
