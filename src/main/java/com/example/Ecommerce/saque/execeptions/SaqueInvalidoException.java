package com.example.Ecommerce.saque.execeptions;

public class SaqueInvalidoException extends RuntimeException {

    public SaqueInvalidoException() {super("O Usuario ja Realizou o Saque dessa Transação!");}

    public SaqueInvalidoException(String message) {super(message);}
    
}
