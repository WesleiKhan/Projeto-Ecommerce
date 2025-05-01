package com.example.Ecommerce.transacoes.saque.execeptions;

public class SaqueInvalidoException extends RuntimeException {

    public SaqueInvalidoException() {super("O Usuario ja Realizou o Saque dessa Transação!");}

    public SaqueInvalidoException(String message) {super(message);}
    
}
