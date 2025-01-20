package com.example.Ecommerce.anuncio_produto.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.utils.exceptions.FreteException;

@ControllerAdvice(basePackages = "com.example.Ecommerce.anuncio_produto.controller.AnuncioControllers")
public class AnuncioExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFound(UserNotFound e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(FreteException.class)
    private ResponseEntity<String> erroCalcularFrete(FreteException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }
    
    @ExceptionHandler(IOException.class)
    private ResponseEntity<String> handlerIOException(IOException e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + "erro ao processar Entrada/Saida" + e.getMessage());
    }
}
