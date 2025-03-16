package com.example.Ecommerce.anuncio_produto.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.Ecommerce.anuncio_produto.exceptions.AnuncioNotFound;
import com.example.Ecommerce.user.exceptions.UserNotAutorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Ecommerce.user.exceptions.UserNotFound;
import com.example.Ecommerce.utils.exceptions.FreteException;

@ControllerAdvice(basePackages = "com.example.Ecommerce.anuncio_produto.controller")
public class AnuncioExceptions extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<String> userNotFound(UserNotFound e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response.get("Erro"));
    }

    @ExceptionHandler(FreteException.class)
    private ResponseEntity<String> erroCalcularFrete(FreteException e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response.get("Erro"));
    }
    
    @ExceptionHandler(IOException.class)
    private ResponseEntity<String> handlerIOException(IOException e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response.get("Erro"));
    }

    @ExceptionHandler(AnuncioNotFound.class)
    private ResponseEntity<String> anuncioNotFound(AnuncioNotFound e) {

        Map<String, String> response = new HashMap<>();

        response.put("Erro", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response.get("Erro"));
    }

    @ExceptionHandler(UserNotAutorization.class)
    private ResponseEntity<String> userNotAutorization(UserNotAutorization e) {

        Map<String, String> response = new HashMap<>();

        response.put("Message", e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response.get("Message"));
    }
}
