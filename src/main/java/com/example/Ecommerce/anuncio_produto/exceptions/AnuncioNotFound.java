package com.example.Ecommerce.anuncio_produto.exceptions;

public class AnuncioNotFound extends RuntimeException{

    public AnuncioNotFound() {super("Anuncio não foi encontrado.");}

    public AnuncioNotFound(String message) {super(message);}
}
